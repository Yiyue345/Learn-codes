package com.example.myapplication.jetpack

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.myapplication.R
import com.example.myapplication.activity.BaseActivity
import com.example.myapplication.databinding.ActivityJetpackBinding
import com.example.myapplication.lei.User
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class JetpackActivity : BaseActivity() {

    private lateinit var binding: ActivityJetpackBinding

    lateinit var viewModel: JetpackViewModel
    lateinit var sp: SharedPreferences

    lateinit var myObserver: MyObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityJetpackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = getPreferences(MODE_PRIVATE)
        val countReserved = sp.getInt("count_reserved", 0)
        viewModel = ViewModelProvider(this, JetpackViewModelFactory(countReserved))[JetpackViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.plusOneBtn.setOnClickListener{
            viewModel.plusOne()
        }
        binding.cleanBtn.setOnClickListener{
            viewModel.clear()
        }

//        viewModel.counter.observe(this, Observer { count ->
//            binding.infoText.text = count.toString()
//            binding.plusOneBtn.text = count.toString() + "++"
//        }) // 观察者看counter中value是否变化
        viewModel.counter.observe(this) { count ->
            binding.infoText.text = count.toString()
            binding.plusOneBtn.text = "$count++"
        } // 两者等价


//        lifecycle.addObserver(MyObserver())

        myObserver = MyObserver(lifecycle)
        lifecycle.addObserver(myObserver)
        /*
       如此便可在任何地方调用lifecycle.currentState来主动获知当前的生命周期状态
        */

        binding.getUserBtn.setOnClickListener{
            val userID = (0..100000).random().toString()
            viewModel.getUser(userID)
        }
        viewModel.user.observe(this) { user ->
            binding.infoText.text = user.firstname
        }

        val userDao = AppDatabase.getDatabase(this).userDao()
        val user1 = User("Tom", "Brady", 999)
        val user2 = User("Tom", "Hanks", 666)

        binding.addDataBtn.setOnClickListener {
            thread {
                user1.id = userDao.insertUser(user1)
                user2.id = userDao.insertUser(user2)
            }
        }
        binding.updateDataBtn.setOnClickListener {
            thread {
                user1.age = 9999
                userDao.updateUser(user1)
            }
        }
        binding.deleteDataBtn.setOnClickListener {
            thread {
                userDao.deleteUserByLastname("Hanks")
            }
        }
        binding.queryDataBtn.setOnClickListener {
            thread {
                for (user in userDao.loadAllUsers()) {
                    Log.d("JetpackActivity", user.toString())
                }
            }
        }

        binding.doWorkBtn.setOnClickListener {
            val request = OneTimeWorkRequest.Builder(SimpleWorker::class.java)
                .setInitialDelay(5, TimeUnit.SECONDS) // 用Java开头的那个
                .addTag("simple")
                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS) // 该如何retry
                .build()


            WorkManager.getInstance(this).enqueue(request)
//            WorkManager.getInstance(this).cancelAllWorkByTag("simple") // 取消有tag的任务
//            WorkManager.getInstance(this).cancelAllWork() // 取消所有任务
            /* 链式运行任务
            val sync = ...
            val compress = ...
            val upload = ...
            WorkManager.getInstance(this)
                .beginWith(sync)
                .then(compress)
                .then(upload)
                .enqueue()
             */
        }



    }

    override fun onPause() {
        super.onPause()
        sp.edit().putInt("count_reserved", viewModel.counter.value ?: 0).apply()

    }


}
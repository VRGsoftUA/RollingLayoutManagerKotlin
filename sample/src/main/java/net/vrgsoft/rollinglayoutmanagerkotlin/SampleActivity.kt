package net.vrgsoft.rollinglayoutmanagerkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import java.util.*




class SampleActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)


        val rollingLayoutManager = RollingLayoutManager(this)
        val simpleAdapter = SimpleAdapter(createTestData(20))

        recyclerView.setLayoutManager(rollingLayoutManager)
        recyclerView.setAdapter(simpleAdapter)
    }

    private fun createTestData(count: Int): List<String> {
        val strings = ArrayList<String>()
        for (i in 0 until count) {
            strings.add(UUID.randomUUID().toString())
        }

        return strings
    }


}

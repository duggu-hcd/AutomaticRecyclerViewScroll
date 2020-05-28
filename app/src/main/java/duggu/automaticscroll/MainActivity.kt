package duggu.automaticscroll

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() ,View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvNews.apply {
            val dataArray : Array<String?> = resources.getStringArray(R.array.list_array)
            adapter = NewsAdapter(dataArray,this@MainActivity)
            layoutManager = llManager
            setHasFixedSize(true)
            addOnItemTouchListener(RecyclerTouchListener(this@MainActivity, object : RecyclerTouchListener.ClickListener {
                @SuppressLint("Recycle")
                override fun onClick(view: View?, position: Int) {
                    dataArray[position%dataArray.size]?.let { model ->
                        Toast.makeText(this@MainActivity,model,Toast.LENGTH_LONG).show()
                    }
                }
            }))
        }
        handler.postDelayed(runnable, speedScroll.toLong())
    }

    private val llManager: LinearLayoutManager = object : LinearLayoutManager(this , HORIZONTAL,false) {
        override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State, position: Int) {
            val smoothScroller: LinearSmoothScroller =
                    object : LinearSmoothScroller(this@MainActivity) {
                        private val SPEED = 4000f // Change this value (default=25f)
                        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                            return SPEED
                        }
                    }
            smoothScroller.targetPosition = position
            startSmoothScroll(smoothScroller)
        }
    }

    private val handler = Handler()
    private val speedScroll = 0
    private val runnable = object : Runnable {
        var count = 0
        override fun run() {
            if (count == rvNews.adapter?.itemCount) count = 0
            if (count < rvNews.adapter?.itemCount?:-1) {
                rvNews.smoothScrollToPosition(++count)
                handler.postDelayed(this, speedScroll.toLong())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, speedScroll.toLong())
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    override fun onClick(v: View?) {
    }
}

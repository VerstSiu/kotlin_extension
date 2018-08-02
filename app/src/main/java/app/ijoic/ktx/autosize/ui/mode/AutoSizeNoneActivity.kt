package app.ijoic.ktx.autosize.ui.mode

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.ijoic.ktx.R
import com.ijoic.ktx.util.AsyncLooper
import kotlinx.android.synthetic.main.act_autosize_none.*

/**
 * Auto size wrap activity.
 *
 * @author verstsiu on 2018/8/1.
 * @version 1.0
 */
class AutoSizeNoneActivity: AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_autosize_none)

    AsyncLooper.handler.postDelayed({
      test_message.text = "I'm Jame"
    }, 2000L)

    AsyncLooper.handler.postDelayed({
      test_message.text = "Nice to meet you"
    }, 4000L)

    AsyncLooper.handler.postDelayed({
      test_message.text = "Thanks"
    }, 6000L)
  }
}
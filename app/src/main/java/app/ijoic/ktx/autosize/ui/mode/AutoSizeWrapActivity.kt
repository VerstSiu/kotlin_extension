package app.ijoic.ktx.autosize.ui.mode

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.ijoic.ktx.R
import com.ijoic.ktx.util.AsyncLooper
import kotlinx.android.synthetic.main.act_autosize_wrap.*

/**
 * Auto size wrap activity.
 *
 * @author verstsiu on 2018/8/1.
 * @version 1.0
 */
class AutoSizeWrapActivity: AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_autosize_wrap)

    AsyncLooper.handler.postDelayed({
      test_message.text = "My name is Tomy"
    }, 2000L)

    AsyncLooper.handler.postDelayed({
      test_message.text = "And"
    }, 4000L)

    AsyncLooper.handler.postDelayed({
      test_message.text = "I have a brother"
    }, 6000L)
  }
}
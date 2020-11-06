package app.ijoic.ktx.autosize.ui.mode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.ijoic.ktx.R
import com.ijoic.ktx.util.AsyncLooper
import kotlinx.android.synthetic.main.act_autosize_reset.*

/**
 * Auto size reset activity.
 *
 * @author verstsiu on 2018/8/1.
 * @version 1.0
 */
class AutoSizeResetActivity: AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_autosize_reset)

    AsyncLooper.handler.postDelayed({
      text_second.text = "is .."
    }, 2000L)

    AsyncLooper.handler.postDelayed({
      text_second.text = "is"
    }, 4000L)
  }
}
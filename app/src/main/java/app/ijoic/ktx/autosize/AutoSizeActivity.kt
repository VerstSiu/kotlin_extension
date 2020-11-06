package app.ijoic.ktx.autosize

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.ijoic.ktx.R
import app.ijoic.ktx.autosize.ui.mode.AutoSizeNoneActivity
import app.ijoic.ktx.autosize.ui.mode.AutoSizeResetActivity
import app.ijoic.ktx.autosize.ui.mode.AutoSizeWrapActivity
import com.ijoic.ktx.content.router.routeTo
import kotlinx.android.synthetic.main.act_autosize.*

/**
 * Auto size activity.
 *
 * @author verstsiu on 2018/8/1.
 * @version 1.0
 */
class AutoSizeActivity: AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_autosize)

    action_mode_none.setOnClickListener { routeTo(Intent(this, AutoSizeNoneActivity::class.java)) }
    action_mode_wrap.setOnClickListener { routeTo(Intent(this, AutoSizeWrapActivity::class.java)) }
    action_mode_reset.setOnClickListener { routeTo(Intent(this, AutoSizeResetActivity::class.java)) }
  }
}
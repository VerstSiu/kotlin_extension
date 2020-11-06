package app.ijoic.ktx

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import app.ijoic.ktx.autosize.AutoSizeActivity
import com.ijoic.ktx.content.router.routeTo
import com.ijoic.ktx.widget.bind.ViewSource
import com.ijoic.ktx.widget.bind.bindView
import com.ijoic.ktx.widget.bind.releaseBindViews

class MainActivity : AppCompatActivity(), ViewSource {

  private val autoSizeButton: View by bindView(R.id.test_case_auto_size)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_main)

    autoSizeButton.setOnClickListener { routeTo(Intent(this, AutoSizeActivity::class.java)) }
  }

  override fun onDestroy() {
    super.onDestroy()
    releaseBindViews()
  }
}

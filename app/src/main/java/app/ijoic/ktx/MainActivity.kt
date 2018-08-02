package app.ijoic.ktx

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.ijoic.ktx.autosize.AutoSizeActivity
import com.ijoic.ktx.content.router.routeTo
import kotlinx.android.synthetic.main.act_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_main)

    test_case_auto_size.setOnClickListener { routeTo(Intent(this, AutoSizeActivity::class.java)) }
  }
}

package app.ijoic.ktx

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.ijoic.ktx.rxjava.execute

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    execute({ "..." }, { Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show() })
  }
}

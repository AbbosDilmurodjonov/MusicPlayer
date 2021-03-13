package uz.abbos_dilmurodjonov.musicplayer

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import uz.abbos_dilmurodjonov.musicplayer.fragments.main.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )
        setContentView(R.layout.activity_main)

        val fragment = supportFragmentManager.findFragmentById(R.id.layoutMainContainer)

        if (fragment == null) {

            supportFragmentManager.beginTransaction()
                .add(R.id.layoutMainContainer, MainFragment())
                .commit()
        }

        /*
            */

    }
}
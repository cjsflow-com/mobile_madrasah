
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.man2superapp.IsiJurnalFragment
import com.example.man2superapp.RiwayatJurnalFragment

class JurnalPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IsiJurnalFragment()
            1 -> RiwayatJurnalFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
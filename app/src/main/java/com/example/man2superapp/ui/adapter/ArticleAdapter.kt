import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.man2superapp.R
import com.example.man2superapp.source.local.model.Article

class ArticleAdapter(private val articles: List<Article>, private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.sliderTitle)
        val image: ImageView = itemView.findViewById(R.id.sliderImage)
        val btnMore: Button = itemView.findViewById(R.id.btnSelengkapnya)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_view, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        Glide.with(holder.itemView.context).load(article.image).into(holder.image)
        holder.btnMore.setOnClickListener {
            val url = "https://m2mpekanbaru.sch.id/articles/${article.id}" // Sesuaikan URL artikel
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            holder.itemView.context.startActivity(intent)
             // onClick("https://m2mpekanbaru.sch.id/articles/${article.id}")
        }
    }

    override fun getItemCount(): Int = articles.size
}

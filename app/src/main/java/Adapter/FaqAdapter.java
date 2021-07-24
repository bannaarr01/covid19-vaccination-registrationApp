package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19vaccination.R;

import java.util.List;

import Model.FaqModel;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqModelVH> {
    List<FaqModel> mFaqModelList;
    int images[];

    public FaqAdapter(List<FaqModel> faqModelList, int[] img) {
        this.mFaqModelList = faqModelList;
        this.images = img;
    }

    @NonNull
    @Override
    public FaqModelVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new FaqModelVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqAdapter.FaqModelVH holder, int position) {

        FaqModel mFaqModel = mFaqModelList.get(position);
        holder.questionFAQ.setText(mFaqModel.getQuestionTitle());
        holder.answerFAQ.setText(mFaqModel.getAnswerDescription());
        holder.imgLogo.setImageResource(images[position]);
        boolean isExpandable = mFaqModelList.get(position).isExpandable();
        holder.mExpandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mFaqModelList.size();
    }

    public class FaqModelVH extends RecyclerView.ViewHolder {
        TextView questionFAQ, answerFAQ;
        LinearLayout mLinearLayout;
        RelativeLayout mExpandableLayout;
        ImageView imgLogo;

        public FaqModelVH(@NonNull View itemView) {
            super(itemView);

            questionFAQ = itemView.findViewById(R.id.tvQuestionFAQ);
            answerFAQ = itemView.findViewById(R.id.tvAnswerFAQ);

            mLinearLayout = itemView.findViewById(R.id.linearLayoutFAQ);
            mExpandableLayout = itemView.findViewById(R.id.expandable_layout);
            imgLogo = itemView.findViewById(R.id.imgFaq);

            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FaqModel mFaqModel = mFaqModelList.get(getAdapterPosition());
                    mFaqModel.setExpandable(!mFaqModel.isExpandable());//expandable
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}

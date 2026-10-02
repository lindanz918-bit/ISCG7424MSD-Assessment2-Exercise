package cs.linda.iscg7424assessment2exercise.exercise2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;

import cs.linda.iscg7424assessment2exercise.R;

public class UserAdapter extends RecyclerView.Adapter<cs.linda.iscg7424assessment2exercise.exercise2.UserAdapter.ViewHolder> {
    List<User> usersList;

    /**
     * Provide a reference to the type of views that you are using
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvFirstName, tvLastName, tvEmail;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            imgAvatar = view.findViewById(R.id.img_avatar);
            tvFirstName = view.findViewById(R.id.tv_first_name);
            tvLastName = view.findViewById(R.id.tv_last_name);
            tvEmail = view.findViewById(R.id.tv_email);
        }
    }

    /**
     * Initialize the dataset of the Adapter
     */
    public UserAdapter(List<User> users) {
        this.usersList = users;
    }


    @NonNull
    @Override
    public cs.linda.iscg7424assessment2exercise.exercise2.UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise2_user_card, parent, false);
        return new cs.linda.iscg7424assessment2exercise.exercise2.UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cs.linda.iscg7424assessment2exercise.exercise2.UserAdapter.ViewHolder holder, int position) {
        User user = usersList.get(position);
        if (user != null) {
            Glide.with(holder.imgAvatar.getContext())
                    .load(user.getAvatar())
                    .placeholder(R.drawable.ic_launcher_foreground) // optional placeholder
                    .error(R.drawable.ic_launcher_foreground)      // optional error image
                    .transform(new CenterCrop(), new RoundedCorners(16))
                    .into(holder.imgAvatar);
            holder.tvFirstName.setText(user.getFirst_name());
            holder.tvLastName.setText(user.getLast_name());
            holder.tvEmail.setText(user.getEmail());
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}

package cs.linda.iscg7424assessment2exercise.exercise1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cs.linda.iscg7424assessment2exercise.R;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    List<Contact> contactsList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Provide a reference to the type of views that you are using
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText etFirstName, etLastName, etEmail, etPhone;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            etFirstName = view.findViewById(R.id.et_first_name);
            etLastName = view.findViewById(R.id.et_last_name);
            etEmail = view.findViewById(R.id.et_email);
            etPhone = view.findViewById(R.id.et_phone);
        }
    }

    /**
     * Initialize the dataset of the Adapter
     */
    public ContactAdapter(List<Contact> contacts) {
        this.contactsList = contacts;
    }


    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise1_contact_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactsList.get(position);
        if (contact != null) {
            holder.etFirstName.setText("First Name: " + contact.getFirst());
            holder.etLastName.setText("Last Name: " + contact.getLast());
            holder.etEmail.setText("Email: " + contact.getEmail());
            holder.etPhone.setText("Phone: " + contact.getPhone());

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(contact);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }
}

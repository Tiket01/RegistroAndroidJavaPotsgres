package com.example.informacionjavapostgresql.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informacionjavapostgresql.R;
import java.util.List;
import java.util.Map;

// Adaptador para el RecyclerView
public class DatosAdapter extends RecyclerView.Adapter<DatosAdapter.DatosViewHolder> {
    private List<Map<String, Object>> datosList;


    public DatosAdapter(List<Map<String, Object>> datosList) {
        this.datosList = datosList;
    }

    @Override
    public DatosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_datos, parent, false);
        return new DatosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DatosViewHolder holder, int position) {
        Map<String, Object> datos = datosList.get(position);

        String nombreCompleto = datos.get("nombres") + " " + datos.get("primer_apellido") + " " + datos.get("segundo_apellido");
        holder.tvNombreCompleto.setText(nombreCompleto);
        holder.tvEdad.setText("Edad: " + datos.get("edad"));
    }

    @Override
    public int getItemCount() {
        return datosList.size();
    }

    public void setDatosList(List<Map<String, Object>> datosList) {
        this.datosList = datosList;
        notifyDataSetChanged();
    }

    public static class DatosViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCompleto, tvEdad;

        public DatosViewHolder(View itemView) {
            super(itemView);
            tvNombreCompleto = itemView.findViewById(R.id.tvNombreCompleto);
            tvEdad = itemView.findViewById(R.id.tvEdad);
        }
    }
}

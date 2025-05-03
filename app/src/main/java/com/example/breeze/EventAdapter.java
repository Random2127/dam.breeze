package com.example.breeze;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(Context context, List<Event> eventos) {
        super(context, 0, eventos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event evento = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_evento, parent, false);
        }

        TextView tvNombre = convertView.findViewById(R.id.tvNombre);
        TextView tvDescripcion = convertView.findViewById(R.id.tvDescripcion);
        TextView tvFechaHora = convertView.findViewById(R.id.tvFechaHora);
        ImageView imgEvento = convertView.findViewById(R.id.imageEvento);

        tvNombre.setText(evento.getNombre());
        tvDescripcion.setText(evento.getDescripcion());
        tvFechaHora.setText(evento.getFecha() + " " + evento.getHora());

        if (evento.getUrlImagen() != null && !evento.getUrlImagen().isEmpty()) {
            Uri uri = Uri.parse(evento.getUrlImagen());
            imgEvento.setImageURI(uri);
        } else {
            imgEvento.setImageResource(R.drawable.breeze_logo);
        }
        return convertView;
    }


}

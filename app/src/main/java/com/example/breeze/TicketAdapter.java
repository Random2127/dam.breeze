package com.example.breeze;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TicketAdapter extends ArrayAdapter<Ticket> {

    public TicketAdapter(Context context, List<Ticket> tickets) {
        super(context, 0, tickets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ticket ticket = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ticket, parent, false);
        }

        TextView nombreEventoView = convertView.findViewById(R.id.txtNombreEvento);
        TextView fechaCompraView = convertView.findViewById(R.id.txtFechaCompra);

        nombreEventoView.setText(ticket.getNombreEvento());
        fechaCompraView.setText("Comprado el: " + ticket.getFechacompra());

        Log.d("TicketAdapter", "Rendering: " + ticket.getNombreEvento());
        return convertView;
    }
}

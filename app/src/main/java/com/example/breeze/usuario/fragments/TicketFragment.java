package com.example.breeze.usuario.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.breeze.GestorBaseDatos;
import com.example.breeze.R;
import com.example.breeze.Ticket;
import com.example.breeze.TicketAdapter;
import com.example.breeze.Utils;

import java.util.ArrayList;

public class TicketFragment extends Fragment {

    protected ArrayList<Ticket> tickets = new ArrayList<>();
    protected GestorBaseDatos gbd;
    protected int clienteID;

    public TicketFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tickets, container, false);
        Utils.cambioSizeTextViews(view, getContext());


        // Recogemos ID
        TicketFragment fragment = new TicketFragment();
        Bundle args = new Bundle();
        args.putInt("clienteID", clienteID);
        fragment.setArguments(args);
        if (args != null)
        {
            int clienteID = args.getInt("clienteID", -1);
            Log.d("TICKET_FRAGMENT", "clienteID received: " + clienteID);
        }


        if (getArguments() != null) {
            clienteID = getArguments().getInt("clienteID", -1);
            Log.d("TicketFragment", "Cliente ID: " + clienteID);
        }

        ListView listaDisponibles = view.findViewById(R.id.listaDisponibles);
        gbd = new GestorBaseDatos(getContext());
        SQLiteDatabase db = gbd.getReadableDatabase();

        // POr que el id nunca se pasa?
        Log.d("TicketFragment", "clienteID: " + clienteID);
        Cursor cursor = db.rawQuery("SELECT t.ticketId, t.eventoID, t.clienteID, t.usado, t.fechaCompra, e.nombre FROM ticket t JOIN evento e ON t.eventoID = e.eventoID WHERE t.clienteID = ?", new String[]{String.valueOf(clienteID)});
        Log.d("TicketFragment", "Cursor count: " + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                Ticket ticket = new Ticket(
                        cursor.getString(0),   // ticketId
                        cursor.getInt(1),      // eventoID
                        cursor.getInt(2),      // clienteID
                        cursor.getInt(3) == 1, // usado (boolean)
                        cursor.getString(4),   // fechaCompra
                        cursor.getString(5)    // nombreEvento
                );
                tickets.add(ticket);
            } while (cursor.moveToNext());
            Log.d("TicketFragment", "Tickets loaded: " + tickets.size());

        }
        TicketAdapter adapter = new TicketAdapter(getContext(), tickets);
        listaDisponibles.setAdapter(adapter);
        return view;
    }

}
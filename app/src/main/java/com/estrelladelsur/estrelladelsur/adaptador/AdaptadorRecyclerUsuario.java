package com.estrelladelsur.estrelladelsur.adaptador;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Usuario;

import java.util.ArrayList;

public class AdaptadorRecyclerUsuario extends
        RecyclerView.Adapter<AdaptadorRecyclerUsuario.UsuarioViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Usuario> usuarioArray;

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {

        private TextView textRecyclerViewUsuario;
        private TextView textRecyclerViewPass;


        public UsuarioViewHolder(View itemView) {
            super(itemView);

            textRecyclerViewUsuario = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewTitulo);
            textRecyclerViewPass = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewFecha);
        }
        public void bindTitular(Usuario usuario) {
            textRecyclerViewUsuario.setText(usuario.getUSUARIO());
            textRecyclerViewPass.setText(usuario.getPASSWORD());
        }
    }

    public AdaptadorRecyclerUsuario(ArrayList<Usuario> usuarioArray) {
        this.usuarioArray = usuarioArray;
    }

    @Override
    public UsuarioViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                 int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_articulo, viewGroup, false);

        itemView.setOnClickListener(this);
        UsuarioViewHolder tvh = new UsuarioViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(UsuarioViewHolder viewHolder, int pos) {
        Usuario item = usuarioArray.get(pos);

        viewHolder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return usuarioArray.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClick(view);
    }
}
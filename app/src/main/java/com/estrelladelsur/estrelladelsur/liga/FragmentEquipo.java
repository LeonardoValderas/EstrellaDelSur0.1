package com.estrelladelsur.estrelladelsur.liga;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.UtilityImage;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerEquipo;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;

public class FragmentEquipo extends Fragment {

	private static final int SELECT_SINGLE_PICTURE = 1;
	private byte[] imagenEscudo = null;
	private RecyclerView recycleViewEquipo;
	private EditText editTextNombre;
	private ImageView imageEquipo;
	private ByteArrayOutputStream baos;
	private Bitmap myImage;
	private Equipo equipoAdeful;
	private ArrayList<Equipo> equipoAdefulArray;
	private AdaptadorRecyclerEquipo adaptador;
	private DialogoAlerta dialogoAlerta;
	private boolean insertar = true;
	private int posicion;
	private ControladorAdeful controladorAdeful;
	private int CheckedPositionFragment;

	public static FragmentEquipo newInstance() {
		FragmentEquipo fragment = new FragmentEquipo();
		return fragment;
	}

	public FragmentEquipo() {
		// Required empty public constructor
	}

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
		controladorAdeful = new ControladorAdeful(getActivity());
		if (state != null) {
			CheckedPositionFragment = state.getInt("curChoice", 0);

		} else {
			init();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_general_liga, container,
				false);
		imageEquipo = (ImageView) v
				.findViewById(R.id.imageButtonEquipo_Cancha);
		editTextNombre = (EditText) v.findViewById(R.id.editTextDescripcion);
		recycleViewEquipo = (RecyclerView) v
				.findViewById(R.id.recycleViewGeneral);
		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("curChoice", CheckedPositionFragment);
	}

	private void init() {

		// imageButton que busca imagen del escudo del equipo en la memoria  interna
		imageEquipo.setImageResource(R.mipmap.ic_escudo_equipo);

		imageEquipo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ImageDialogEquipo();

			}
		});
		editTextNombre.setHintTextColor(Color.GRAY);

		recycleViewEquipo.addOnItemTouchListener(new RecyclerTouchListener(
				getActivity(), recycleViewEquipo, new ClickListener() {

			@Override
			public void onLongClick(View view, final int position) {

				dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
						"Desea eliminar el equipo?", null, null);
				dialogoAlerta.btnAceptar.setText("Aceptar");
				dialogoAlerta.btnCancelar.setText("Cancelar");

				dialogoAlerta.btnAceptar
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								controladorAdeful.abrirBaseDeDatos();
								if (controladorAdeful
										.eliminarEquipoAdeful(equipoAdefulArray
												.get(position)
												.getID_EQUIPO())) {
									controladorAdeful.cerrarBaseDeDatos();

									recyclerViewLoadEquipo();

									Toast.makeText(
											getActivity(),
											"Equipo Eliminado Correctamente",
											Toast.LENGTH_SHORT).show();
									imageEquipo.setImageResource(R.mipmap.ic_escudo_equipo);
									editTextNombre.setText("");
									insertar = true;
									dialogoAlerta.alertDialog.dismiss();
								} else {
									controladorAdeful.cerrarBaseDeDatos();
									Toast.makeText(getActivity(), "Error en la base de datos interna, vuelva a intentar." +
													"\n Si el error persiste comuniquese con soporte.",
											Toast.LENGTH_SHORT).show();
								}
							}
						});
				dialogoAlerta.btnCancelar
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialogoAlerta.alertDialog.dismiss();
							}
						});
			}

			@Override
			public void onClick(View view, int position) {
				// TODO Auto-generated method stub

				insertar = false;
				imageEquipo.setImageResource(R.mipmap.ic_escudo_equipo);
				editTextNombre.setText(equipoAdefulArray.get(position)
						.getNOMBRE_EQUIPO());
				if (equipoAdefulArray.get(position).getESCUDO() != null) {
					Bitmap theImage = BitmapFactory
							.decodeByteArray(
									equipoAdefulArray.get(position)
											.getESCUDO(), 0,
									equipoAdefulArray.get(position)
											.getESCUDO().length);
					theImage = Bitmap.createScaledBitmap(theImage, 150,
							150, true);

					imageEquipo.setImageBitmap(theImage);
					imagenEscudo = equipoAdefulArray.get(position)
							.getESCUDO();
				}
				posicion = position;
			}
		}));

		recyclerViewLoadEquipo();
	}

	public void recyclerViewLoadEquipo() {

		recycleViewEquipo.setLayoutManager(new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false));
		recycleViewEquipo.addItemDecoration(new DividerItemDecoration(
				getActivity(), DividerItemDecoration.VERTICAL_LIST));
		recycleViewEquipo.setItemAnimator(new DefaultItemAnimator());

		controladorAdeful.abrirBaseDeDatos();
		equipoAdefulArray = controladorAdeful.selectListaEquipoAdeful();
		if(equipoAdefulArray != null) {
			controladorAdeful.cerrarBaseDeDatos();

			adaptador = new AdaptadorRecyclerEquipo(equipoAdefulArray);
			recycleViewEquipo.setAdapter(adaptador);
		}else{
			controladorAdeful.cerrarBaseDeDatos();
			Toast.makeText(getActivity(), "Error en la base de datos interna, vuelva a intentar." +
							"\n Si el error persiste comuniquese con soporte.",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void ImageDialogEquipo() {

		AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
				getActivity());
		myAlertDialog.setTitle("Galeria");
		myAlertDialog.setMessage("Seleccione un Escudo");

		myAlertDialog.setPositiveButton("Gallery",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						UtilityImage.pictureActionIntent = new Intent(
								Intent.ACTION_GET_CONTENT, null);
						UtilityImage.pictureActionIntent.setType("image/*");
						UtilityImage.pictureActionIntent.putExtra(
								"return-data", true);
						startActivityForResult(
								UtilityImage.pictureActionIntent,
								UtilityImage.GALLERY_PICTURE);
					}
				});

		myAlertDialog.show();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == UtilityImage.GALLERY_PICTURE) {

			SeleccionarImagen(data);
		}
	}

	public static Bitmap createDrawableFromView(View view) {

		view.setDrawingCacheEnabled(true);
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache(true);
		Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);

		return bitmap;
	}

	public void SeleccionarImagen(Intent data) {
		try {
			UtilityImage.uri = data.getData();

			if (UtilityImage.uri != null) {

				Cursor cursor = getActivity().getContentResolver().query(
						UtilityImage.uri, null, null, null, null);

				cursor.moveToFirst();
				String document_id = cursor.getString(0);
				document_id = document_id.substring(document_id
						.lastIndexOf(":") + 1);

				cursor = getActivity()
						.getContentResolver()
						.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								null, MediaStore.Images.Media._ID + " = ? ",
								new String[] { document_id }, null);
				cursor.moveToFirst();
				String path = cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.Media.DATA));
				cursor.close();

				// Assign string path to File
				UtilityImage.Default_DIR = new File(path);

				// Create new dir MY_IMAGES_DIR if not created and copy image
				// into that dir and store that image path in valid_photo
				UtilityImage.Create_MY_IMAGES_DIR();

				// Copy your image
				UtilityImage.copyFile(UtilityImage.Default_DIR,
						UtilityImage.MY_IMG_DIR);

				// Get new image path and decode it
				Bitmap b = UtilityImage
						.decodeFile(UtilityImage.Paste_Target_Location);

				// use new copied path and use anywhere
				String valid_photo = UtilityImage.Paste_Target_Location
						.toString();
				b = Bitmap.createScaledBitmap(b, 150, 150, true);

				// set your selected image in image view
				imageEquipo.setImageBitmap(b);
				cursor.close();

				baos = new ByteArrayOutputStream();
				b.compress(CompressFormat.PNG, 0, baos);
				imagenEscudo = baos.toByteArray();

			} else {
				Toast toast = Toast.makeText(getActivity(),
						"No se selecciono un escudo.", Toast.LENGTH_LONG);
				toast.show();
			}
		} catch (Exception e) {
			// you get this when you will not select any single image
			Log.e("onActivityResult", "" + e);

		}
	}

	public static interface ClickListener {

		public void onClick(View view, int position);
		public void onLongClick(View view, int position);
	}

	static class RecyclerTouchListener implements
			RecyclerView.OnItemTouchListener {

		private GestureDetector detector;
		private ClickListener clickListener;

		public RecyclerTouchListener(Context context,
				final RecyclerView recyclerView,
				final ClickListener clickListener) {
			this.clickListener = clickListener;
			detector = new GestureDetector(context,
					new GestureDetector.SimpleOnGestureListener() {

						@Override
						public boolean onSingleTapUp(MotionEvent e) {
							return true;
						}

						@Override
						public void onLongPress(MotionEvent e) {
							View child = recyclerView.findChildViewUnder(
									e.getX(), e.getY());
							if (child != null && clickListener != null) {
								clickListener.onLongClick(child,
										recyclerView.getChildPosition(child));
							}
						}
					});
		}

		@Override
		public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
			// TODO Auto-generated method stub
			View child = rv.findChildViewUnder(e.getX(), e.getY());
			if (child != null && clickListener != null
					&& detector.onTouchEvent(e)) {
				clickListener.onClick(child, rv.getChildPosition(child));
			}
			return false;
		}

		@Override
		public void onTouchEvent(RecyclerView rv, MotionEvent e) {

		}

		@Override
		public void onRequestDisallowInterceptTouchEvent(boolean arg0) {
			// TODO Auto-generated method stub

		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
		inflater.inflate(R.menu.menu_administrador_general, menu);
		// menu.getItem(0).setVisible(false);//usuario
		// menu.getItem(1).setVisible(false);//permiso
		// menu.getItem(2).setVisible(false);//lifuba
		menu.getItem(3).setVisible(false);// adeful
		menu.getItem(4).setVisible(false);// puesto
		menu.getItem(5).setVisible(false);// posicion
		// menu.getItem(6).setVisible(false);// cargo
		// menu.getItem(7).setVisible(false);//cerrar
		// menu.getItem(8).setVisible(false);// guardar
		menu.getItem(9).setVisible(false);// Subir
		menu.getItem(10).setVisible(false); // eliminar
		menu.getItem(11).setVisible(false); // consultar
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == R.id.action_usuario) {

            /*Intent usuario = new Intent(getActivity(),
                    NavigationDrawerUsuario.class);
            startActivity(usuario);*/

			return true;
		}

		if (id == R.id.action_permisos) {
			return true;
		}

		if (id == R.id.action_guardar) {

			if (editTextNombre.getText().toString().equals("")) {
				Toast.makeText(getActivity(),
						"Ingrese el nombre del equipo.", Toast.LENGTH_SHORT)
						.show();
			} else {

				if (insertar) {

					String usuario = "Administrador";
					String fechaCreacion = controladorAdeful.getFechaOficial();
					String fechaActualizacion = fechaCreacion;


					equipoAdeful = new Equipo(0, editTextNombre.getText()
							.toString(), imagenEscudo, usuario,
							fechaCreacion, usuario, fechaActualizacion);

					controladorAdeful.abrirBaseDeDatos();
					if(controladorAdeful.insertEquipoAdeful(equipoAdeful)) {
						controladorAdeful.cerrarBaseDeDatos();
						recyclerViewLoadEquipo();
						editTextNombre.setText("");
						if (imagenEscudo != null) {
							imageEquipo.setImageResource(R.mipmap.ic_escudo_equipo);
						}
						Toast.makeText(getActivity(),
								"Equipo cargado correctamente.",
								Toast.LENGTH_SHORT).show();
						imagenEscudo = null;
					}else{
						controladorAdeful.cerrarBaseDeDatos();
						Toast.makeText(getActivity(), "Error en la base de datos interna, vuelva a intentar." +
										"\n Si el error persiste comuniquese con soporte.",
								Toast.LENGTH_SHORT).show();
					}

				} else {

					String usuario = "Administrador";
					String fechaActualizacion = controladorAdeful
							.getFechaOficial();

					equipoAdeful = new Equipo(equipoAdefulArray.get(
							posicion).getID_EQUIPO(), editTextNombre
							.getText().toString(), imagenEscudo, null,
							null,usuario, fechaActualizacion);

					controladorAdeful.abrirBaseDeDatos();
					if(controladorAdeful.actualizarEquipoAdeful(equipoAdeful)) {
						controladorAdeful.cerrarBaseDeDatos();
						recyclerViewLoadEquipo();
						editTextNombre.setText("");
						insertar = true;
						imageEquipo.setImageResource(R.mipmap.ic_escudo_equipo);
						editTextNombre.setText("");
						Toast.makeText(getActivity(),
								"Equipo actualizado correctamente.",
								Toast.LENGTH_SHORT).show();
						imagenEscudo = null;
					}else{
						controladorAdeful.cerrarBaseDeDatos();
						Toast.makeText(getActivity(), "Error en la base de datos interna, vuelva a intentar." +
										"\n Si el error persiste comuniquese con soporte.",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
			return true;
		}
		if (id == android.R.id.home) {

			NavUtils.navigateUpFromSameTask(getActivity());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
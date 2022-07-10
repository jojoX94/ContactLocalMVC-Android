package com.futurmap.contactsqlite.screens.home;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dolatkia.animatedThemeManager.AppTheme;
import com.dolatkia.animatedThemeManager.ThemeFragment;
import com.dolatkia.animatedThemeManager.ThemeManager;
import com.futurmap.contactsqlite.R;
import com.futurmap.contactsqlite.database.UserContactDatabase;
import com.futurmap.contactsqlite.databinding.FragmentHomeBinding;
import com.futurmap.contactsqlite.model.UserContact;
import com.futurmap.contactsqlite.screens.RootActivity;
import com.futurmap.contactsqlite.theme.MyAppTheme;
import com.futurmap.contactsqlite.utils.File;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends ThemeFragment {
    public static String imageString = "";
    FragmentHomeBinding binding;
    ActivityResultLauncher galleryLauncher;
    ActivityResultLauncher<Intent> cameraLauncher;
    UserContactDatabase db;
    private Uri tempImage;
    private Bitmap ImageSelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        changeLangage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        db = new UserContactDatabase(getContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeLangage();
        requirePermission();
        initDropDownList();
        onClickListener();
        handleImage();
        Log.i("db", "=>" + db.getAllContacts());
        Log.i("theme", "" + ThemeManager.Companion.getInstance().getCurrentTheme());

    }

    public void changeLangage() {
        binding.boxName.setHint(getText(R.string.home_lbl_name));
        binding.boxGroup.setHint(getText(R.string.home_lbl_group));
    }

    private void initDropDownList() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Famille");
        stringList.add("Amie");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.list_dropdown_item, getResources().getStringArray(R.array.group));
        binding.txtAttribution.setAdapter(adapter);
    }

    public void handleImage() {
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {

//                        tempImage = result;
//                        ImageSelected = File.getThumb(result, getContext());
//                        binding.btnAddImage.setImageURI(result);
                        try {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageSelected = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result);
                            // Log.d(TAG, String.valueOf(bitmap));
                            ImageSelected.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageBytes = baos.toByteArray();
                            imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                            //decode base64 string to image
                            imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                            Log.e("mybitmap", String.valueOf(imageString));


                            binding.btnAddImage.setImageBitmap(decodedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        binding.btnAddImage.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                });
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");

                        WeakReference<Bitmap> result1 = new WeakReference<>(Bitmap.createScaledBitmap(imageBitmap, imageBitmap.getHeight(), imageBitmap.getWidth(), false).copy(Bitmap.Config.RGB_565, true));
                        Bitmap bm = result1.get();
                        tempImage = File.saveImage(bm, getContext());
                        ImageSelected = File.getThumb(tempImage, getContext());
                        binding.btnAddImage.setImageURI(tempImage);
                        binding.btnAddImage.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                });
    }

    public void onClickListener() {
        binding.btnAddImage.setOnClickListener(v -> {
            showBottomSheetDialog();
        });
        binding.btnCreateAccount.setOnClickListener(v -> {
//            NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_updateFragment);
            if (!binding.txtAttribution.getText().toString().equals("") && !binding.txtName.getText().toString().equals("") && !binding.boxPhone.getText().toString().equals("")) {
                binding.boxCodePicker.registerCarrierNumberEditText(binding.boxPhone);
                if (binding.boxCodePicker.isValidFullNumber()) {
                    UserContact userContact = new UserContact(binding.txtName.getText().toString(), binding.txtAttribution.getText().toString(), binding.boxCodePicker.getFormattedFullNumber());
                    if (!imageString.equals("")) {
//                        userContact.setImage(imageString);
                    }
                    RootActivity.contactList.add(userContact);
                    RootActivity.adapter.notifyDataSetChanged();
                    db.addContact(userContact);
                    Log.i("db", "=>>>>>>>" + db.getContact(1));
//                    viewModel.insert(userContact);
                    clearValue();
                } else {
                    binding.boxPhone.setError("This phone number is not valid");
                }
            } else if (binding.txtName.getText().toString().equals("")) {
                binding.txtName.setError("This field is mandatory");
            } else if (binding.txtAttribution.getText().toString().equals("")) {
                binding.txtAttribution.setError("This field is mandatory");
            } else if (binding.boxPhone.getText().toString().equals("")) {
                binding.boxPhone.setError("This field is mandatory");
            }

        });
    }

    private void clearValue() {
        binding.txtName.setText("");
//        binding.boxPhone.setText("");
        binding.btnAddImage.setImageResource(R.drawable.ic_outline_photo_camera_45);
        binding.btnAddImage.setScaleType(ImageView.ScaleType.CENTER);
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.select_photo_dialog);

        ImageButton camera = bottomSheetDialog.findViewById(R.id.btn_cam);
        ImageButton gallery = bottomSheetDialog.findViewById(R.id.btn_gall);
        camera.setOnClickListener(v -> {
            cameraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            bottomSheetDialog.dismiss();
        });
        gallery.setOnClickListener(v -> {
            galleryLauncher.launch("image/*");
            bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.show();
    }

    public void requirePermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
    }

    @Override
    public void syncTheme(@NonNull AppTheme appTheme) {
        MyAppTheme theme = (MyAppTheme) appTheme;
        binding.scrollview.setBackgroundColor(theme.primaryBackground(getContext()));
        binding.btnAddImage.setBackgroundColor(theme.addImageBg(getContext()));
        binding.textView.setTextColor(theme.secondaryText(getContext()));
        binding.boxName.setBoxBackgroundColor(theme.bgInput(getContext()));
        binding.boxName.getStartIconDrawable().setTint(theme.primaryIcon(getContext()));
//        binding.boxName.setHintTextAppearance();
        Log.i("box", binding.boxGroup.getBoxBackgroundColor() + "");
        binding.boxGroup.setBoxBackgroundColor(theme.bgInput(getContext()));
        binding.boxGroup.getStartIconDrawable().setTint(theme.primaryIcon(getContext()));
        binding.lblBoxPhone.setTextColor(theme.primaryText(getContext()));
        binding.textView3.setTextColor(theme.secondaryText(getContext()));
        binding.boxPhone.setHintTextColor(theme.primaryText(getContext()));
        binding.btnCreateAccount.setBackgroundColor(theme.primaryBtnBackground(getContext()));
        binding.btnCreateAccount.setTextColor(theme.btnText(getContext()));
    }
}
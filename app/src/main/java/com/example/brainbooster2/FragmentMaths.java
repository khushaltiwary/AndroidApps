package com.example.brainbooster2;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMaths#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMaths extends Fragment {

    /** Custom Declaration for Firestore */
    private FirebaseFirestore dbroot = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    ArrayList<FirestoreUser> firestoreUserArrayList;
    RecyclerVMathsAdapter recyclerVMathsAdapter;
    ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentMaths() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMaths.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMaths newInstance(String param1, String param2) {
        FragmentMaths fragment = new FragmentMaths();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maths, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data.....");
        progressDialog.show();

        recyclerView = view.findViewById(R.id.recyclerVFragMaths);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firestoreUserArrayList = new ArrayList<FirestoreUser>();
        recyclerVMathsAdapter = new RecyclerVMathsAdapter(getContext(),firestoreUserArrayList);

        recyclerView.setAdapter(recyclerVMathsAdapter);
        getFireStoreData();
        return view;
    }

    private void getFireStoreData() {
        dbroot.collection("users").orderBy("userMathsScore", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Toast.makeText(getContext(), "Error occured while fetching..", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for(DocumentChange documentChange : value.getDocumentChanges()){

                            if(documentChange.getType() == DocumentChange.Type.ADDED){
                                firestoreUserArrayList.add(documentChange.getDocument().toObject(FirestoreUser.class));
                            }

                            recyclerVMathsAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }

                    }
                });
    }
}
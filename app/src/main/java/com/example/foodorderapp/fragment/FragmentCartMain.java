package com.example.foodorderapp.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.FoodCartAdapter;
import com.example.foodorderapp.model.BillFood;
import com.example.foodorderapp.model.Cart;
import com.example.foodorderapp.model.Customer;
import com.example.foodorderapp.model.Food;
import com.example.foodorderapp.viewmodel.CartMainViewModel;
import com.example.foodorderapp.viewmodel.CartViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FragmentCartMain extends Fragment {
    private NavController navController;
    private FoodCartAdapter foodCartAdapter;
    private List<Cart> listCart;
    private List<Cart> listCartSell = new ArrayList<>();
    private TextView tvFoodCartSum, tvBuyFoodCart;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private View mView;
    private CartViewModel cartViewModel;
    private int sum =0;
    private CartMainViewModel cartMainViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_cart_main, container, false);
        initUI();
        onClickBuyFoodCart();
        return mView;
    }


    private void onClickBuyFoodCart() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String uid = currentUser.getUid();
        DocumentReference docRef = db.collection("customers").document(uid);

        tvBuyFoodCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listCartSell.size() == 0) {
                    Toast.makeText(getActivity(), "Lựa chọn đơn hàng!", Toast.LENGTH_SHORT).show();
                }
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (!document.exists()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Bạn chưa cập nhật thông tin cá nhân bạn có muốn di chuyển đến cập nhật thông tin không?")
                                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                navController.navigate(R.id.action_fragmentCart_to_fragmentProfileInfomation);

                                            }
                                        })
                                        .setNegativeButton("Không", null)
                                        .show();


                            }else {
                                Customer customer = document.toObject(Customer.class);
                                String name =customer.getName().toString();
                                String phone = customer.getNumberphone().toString();
                                String location =  String.valueOf(customer.getLocation());
                                Log.d("checked", name + phone);
                                Log.d("checked", String.valueOf(customer.getLocation()) );
                                if(location == "null"){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Bạn chưa cập nhật địa chỉ bạn có muốn di chuyển đến địa chỉ không?")
                                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    navController.navigate(R.id.action_fragmentCart_to_fragmentProfileLocation);

                                                }
                                            })
                                            .setNegativeButton("Không", null)
                                            .show();

                                }else {
                                    Gson gson = new Gson();
                                    String json = gson.toJson(listCartSell);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("listCartPay", json);
                                    navController.navigate(R.id.action_fragmentCart_to_fragmentCartMainPay, bundle);
                                }

                            }
                        }
                    }
                });


            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
    }

    private void initUI() {
        recyclerView = mView.findViewById(R.id.rcv_cart);
        tvFoodCartSum = mView.findViewById(R.id.tvfoodcart_sum);
        tvBuyFoodCart = mView.findViewById(R.id.tv_buyfoodcart);
        progressDialog = new ProgressDialog(getActivity());
        cartViewModel = new CartViewModel();

        listCart = new ArrayList<>();
        getListCart();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        foodCartAdapter = new FoodCartAdapter(getActivity(), listCart, new FoodCartAdapter.IClickItem() {
            @Override
            public void clickDeleteFoodCart(Cart cart, String documentSnapshot) {
                documentSnapshot = cart.getId();
                cartViewModel.deleteFoodCart(cart, documentSnapshot);

            }


            // Lỗi  thay đổi chú ý sửa
            @Override
            public void clickAddCheckBoxFoodToCart(Cart cart, boolean ischecked) {

                if(ischecked){
                    listCartSell.add(cart);
                    sum += Integer.parseInt(cart.getFood().getPrice()) * cart.getAmount();
                    tvFoodCartSum.setText(String.valueOf(sum) + " đ");
                    mView.invalidate();

                }else{
                    listCartSell.remove(cart);
                    sum -= Integer.parseInt(cart.getFood().getPrice()) * cart.getAmount();
                    if(sum < 0) sum = 0;
                    tvFoodCartSum.setText(String.valueOf(sum) + " đ");
                    mView.invalidate();
                }
                Log.d("documentfood", cart + String.valueOf(ischecked) + (sum));
            }

            @Override
            public void updateAmountFood(Cart cart, int amount, String documentSnapshot) {
                documentSnapshot = cart.getId();
                cart.setAmount(amount);
                cartViewModel.updateFoodCart(amount, documentSnapshot);
                Log.d("documentfood", amount + documentSnapshot);
            }
        });

        recyclerView.setAdapter(foodCartAdapter);

//        cartMainViewModel = new ViewModelProvider(this).get(CartMainViewModel.class);
//        cartMainViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Cart>>() {
//            @Override
//            public void onChanged(List<Cart> carts) {
//
//            }
//        });







        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        int currentDestinationId = navController.getCurrentDestination().getId();
        if (currentDestinationId == R.id.fragmentCartMain) {
            Log.d("initUI", "dadsdasdasdsadsa");
            Toast.makeText(getActivity(), "fđfdfdfdfsdfdfsdf", Toast.LENGTH_SHORT).show();
        }else if(currentDestinationId == R.id.fragmentCart){
            Log.d("initUI", "huyddaas");
        }

    }

    private void getListCart() {
        progressDialog.show();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String uid = currentUser.getUid();
        DocumentReference cardRef = database.collection("customers").document(uid);
        CollectionReference collectionReference = cardRef.collection("carts");
//        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        if(document == null){
//                            return;
//                        }
//                        String documentID = document.getId();
//                        Food food = document.get("food", Food.class);
//                        int amount = document.get("amount", Integer.class);
//                        Cart cart  = new Cart(food, amount);
//                        cart.setId(documentID);
//                        Log.d("getFoodCart", food.getName()
//                                + food.getPrice() + food.getNameRestaurant() + documentID +amount);
//
//                        listCart.add(cart);
//                    }
//                    progressDialog.dismiss();
//                    foodCartAdapter.notifyDataSetChanged();
//
//                } else {
//                    progressDialog.dismiss();
//                    Toast.makeText(getActivity(), "Không có đơn hàng!", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>(){

            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // Xử lý lỗi nếu có
                    return;
                }
                if (value != null ) {
                    listCart.clear();
                    for (DocumentSnapshot document : value.getDocuments()) {
                        String documentID = document.getId();
                        Food food = document.get("food", Food.class);
                        int amount = document.get("amount", Integer.class);
                        Cart cart  = new Cart(food, amount);
                        cart.setId(documentID);
                        Log.d("getFoodCart", food.getName()
                                + food.getPrice() + food.getNameRestaurant() + documentID +amount);

                        listCart.add(cart);
                    }
                    progressDialog.dismiss();
                    foodCartAdapter.notifyDataSetChanged();
                }
            }
        });









    }
}

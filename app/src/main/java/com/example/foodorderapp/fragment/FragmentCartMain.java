package com.example.foodorderapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.FoodCartAdapter;
import com.example.foodorderapp.model.Cart;
import com.example.foodorderapp.model.Food;
import com.example.foodorderapp.viewmodel.CartViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentCartMain extends Fragment {
    private NavController navController;
    private FoodCartAdapter foodCartAdapter;
    private List<Cart> listCart, listCartSell;
    private TextView tvFoodCartSum, tvBuyFoodCart;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private View mView;
    private CartViewModel cartViewModel;
    private int sum =0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_cart_main, container, false);
        initUI();
        onClickBuyFoodCart();
        return mView;
    }

    private void onClickBuyFoodCart() {
        tvBuyFoodCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listCartSell == null){
                    Toast.makeText(getActivity(), "Lựa chọn đơn hàng!", Toast.LENGTH_SHORT).show();
                }else{
                    FragmentCartMainPay fragment = new FragmentCartMainPay();
                    FragmentManager fragmentManager = getParentFragmentManager();


                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, fragment)
                            .addToBackStack(null)
                            .commit();
//                    Intent intent = new Intent(getActivity(), FragmentCartMainPay.class);
//                    startActivity(intent);
//                    navController.navigate(R.id.action_fragmentCart_to_fragmentCartMainPay);
                }
            }
        });
    }

    private void initUI() {
        recyclerView = mView.findViewById(R.id.rcv_cart);
        tvFoodCartSum = mView.findViewById(R.id.tvfoodcart_sum);
        tvBuyFoodCart = mView.findViewById(R.id.tv_buyfoodcart);
        progressDialog = new ProgressDialog(getActivity());
        cartViewModel = new CartViewModel();

        listCart = new ArrayList<>();
        listCartSell = new ArrayList<>();
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

    }

    private void getListCart() {
        progressDialog.show();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String uid = currentUser.getUid();
        DocumentReference cardRef = database.collection("customers").document(uid);
        CollectionReference collectionReference = cardRef.collection("carts");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document == null){
                            return;
                        }
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

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Không có đơn hàng!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}

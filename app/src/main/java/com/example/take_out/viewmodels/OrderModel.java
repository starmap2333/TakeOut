package com.example.take_out.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.take_out.data.MyOrder;
import com.example.take_out.service.ApiResult;
import com.example.take_out.service.ResultCode;
import com.example.take_out.service.Service;

import java.util.ArrayList;
import java.util.List;

public class OrderModel extends ViewModel {
    private MutableLiveData<ApiResult<List<MyOrder>>> _orderList;

    private MutableLiveData<ApiResult<List<MyOrder>>> _getOrderList() {
        if (_orderList == null) {
            _orderList = Service.INSTANCE.getMyOrderService().getMyOrderByUserId(22);
        }
        return _orderList;
    }

    public LiveData<List<MyOrder>> getOrderList() {
        return Transformations.map(_getOrderList(), it -> {
            List<MyOrder> data = it.getData();
            if (data == null)
                return new ArrayList<>();
            else return data;
        });
    }

    public LiveData<Boolean> getReady() {
        return Transformations.map(_getOrderList(),
                it -> it.getCode() == ResultCode.SUCCESS.getCode());
    }
}

package com.digione.zgb2b.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.common.Constants.MsgCode;
import com.digione.zgb2b.utils.ShopCartUtils;
import com.digione.zgb2b.utils.StringUtils;
import com.digione.zgb2b.utils.ToastUtil;

public class MyAlertDialogFragment extends DialogFragment {

	private Activity activity;
	private Button add;
	private Button remove;
	private EditText quantityEditText;

	private TextView linkQuantityTV;
	private TextView linkMoneyTV;
	private Double price;
	private Integer productId;
	private Integer quantity;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MsgCode.EDIT_SHOP_CART_SUCCESS: {
				linkQuantityTV.setText(quantityEditText.getText().toString());
				linkMoneyTV.setText("产品费用:"
						+ StringUtils.formatMoney(Integer.parseInt(quantityEditText.getText().toString()) * price));
				break;
			}
			case MsgCode.EDIT_SHOP_CART_FAILURE:
				break;

			default:
				break;
			}
		}
	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = LayoutInflater.from(activity).inflate(R.layout.my_dialog, null);
		add = (Button) view.findViewById(R.id.my_dialog_add);
		remove = (Button) view.findViewById(R.id.my_dialog_remove);
		quantityEditText = (EditText) view.findViewById(R.id.my_dialog_edit_text);
		quantityEditText.setText(quantity.toString());
		quantityEditText.setSelectAllOnFocus(true);
		add.setOnClickListener(addOnClickListener);
		remove.setOnClickListener(removOnClickListener);
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setView(view);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (!StringUtils.isEmpty(quantityEditText.getText().toString())
						&& Integer.parseInt(quantityEditText.getText().toString()) > 0) {
					ShopCartUtils.getInstance(mHandler).updateShopCart(activity, productId.toString(),
							Integer.parseInt(quantityEditText.getText().toString()));
				} else {
					ToastUtil.showToast(activity, getResources().getString(R.string.num_mor_than_zeao), 1000);
				}
			}
		});

		AlertDialog dialog = builder.create();
		return dialog;
	}

	private OnClickListener addOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			final int arg = Integer.parseInt(quantityEditText.getText().toString());
			if (arg < 999 && arg >= 0) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						quantityEditText.setText((arg + 1) + "");
					}
				});
			}
		}
	};

	private OnClickListener removOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			final int arg = Integer.parseInt(quantityEditText.getText().toString());
			if (arg > 1) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						quantityEditText.setText((arg - 1) + "");
					}
				});

			}
		}
	};

	public TextView getLinkQuantityTV() {
		return linkQuantityTV;
	}

	public void setLinkQuantityTV(TextView linkQuantityTV) {
		this.linkQuantityTV = linkQuantityTV;
	}

	public TextView getLinkMoneyTV() {
		return linkMoneyTV;
	}

	public void setLinkMoneyTV(TextView linkMoneyTV) {
		this.linkMoneyTV = linkMoneyTV;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}

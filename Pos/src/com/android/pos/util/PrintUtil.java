package com.android.pos.util;

import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.RT_Printer.BluetoothPrinter.BLUETOOTH.BluetoothPrintDriver;
import com.android.pos.Constant;
import com.android.pos.cashier.CashierActivity;
import com.android.pos.cashier.CashierPaymentDeviceListActivity;
import com.android.pos.dao.Merchant;
import com.android.pos.dao.TransactionItem;
import com.android.pos.dao.Transactions;

public class PrintUtil {

	// Printer Configuration

	// Debugging
	private static final String TAG = "BloothPrinterActivity";
	private static final boolean D = true;

	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Intent request codes
	public static final int REQUEST_CONNECT_DEVICE = 1;
	public static final int REQUEST_ENABLE_BT = 2;
	
	public static int revBytes = 0;
	public static boolean isHex = false;
	
	public static int mPrinterLineSize = 30;
	
	public static final int REFRESH = 8;

	// Name of the connected device
	private static String mConnectedDeviceName = null;
	// Local Bluetooth adapter
	private static BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the chat services
	private static BluetoothPrintDriver mChatService = null;
	
	private static CashierActivity mActivity;
	
	public static void setPrinterLineSize(int printerLineSize) {
		
		mPrinterLineSize = printerLineSize;
	}
	
	public static boolean initBluetooth(CashierActivity activity) {
		
		boolean isActivated = false;
		
		mActivity = activity;

		// Get local Bluetooth adapter
		
		if (mBluetoothAdapter == null) {
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		}

		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult

		if (mBluetoothAdapter == null) {
			mActivity.setMessage("Bluetooth tidak tersedia. Anda tidak dapat melakukan print nota.");

		} else {

			if (!mBluetoothAdapter.isEnabled()) {

				mActivity.setMessage("Bluetooth tidak aktif. Mohon aktifkan bluetooth anda");
				Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				
				mActivity.startActivityForResult(enableIntent, REQUEST_ENABLE_BT);

			} else {
				
				isActivated = true;
				
				if (mChatService == null) {
					
					setupChat();
					
					//mActivity.setMessage("Printer tidak terhubung, aktifkan Bluetooth Printer anda dan hubungkan ke sistem");
					//setupChat();
				
				} else {
					
					if (BluetoothPrintDriver.IsNoConnection()) {
						//mActivity.setMessage("Printer tidak terhubung, aktifkan Bluetooth Printer anda dan hubungkan ke sistem");
					}
				} 
			}
		}
		
		return isActivated;
	}
	
	public static void setupChat() {

		if (mChatService == null) {
			
			mChatService = new BluetoothPrintDriver(mActivity, mHandler);
		}
	}
	
	public static void connectToBluetoothPrinter(String address) {
		
		System.out.println("Connecting to BT Printer : " + address);
		
		// Get Bluetooth driver
		if (mBluetoothAdapter == null) {
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		}
		
		// Get the BLuetoothDevice object
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

		// Attempt to connect to the device
		mChatService.connect(device);
	}
	
	public static void selectBluetoothPrinter() {
		
		setupChat();
		
		//if (mBluetoothAdapter != null && 
		//	BluetoothPrintDriver.IsNoConnection()) {
		
		if (mBluetoothAdapter != null) {
			
			mChatService.stop();
			mChatService.start();
			
			Intent serverIntent = new Intent(mActivity, CashierPaymentDeviceListActivity.class);
			mActivity.startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
		}
	}

	// The Handler that gets information back from the BluetoothChatService
	private static final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case MESSAGE_STATE_CHANGE:

				switch (msg.arg1) {
				
				case BluetoothPrintDriver.STATE_CONNECTED:

					mActivity.showMessage("Terkoneksi dengan Printer Bluetooth : " + mConnectedDeviceName);
					mActivity.clearMessage();
					
					break;

				case BluetoothPrintDriver.STATE_CONNECTING:

					mActivity.showMessage("Melaksanakan koneksi ke Printer Bluetooth");
					break;

				case BluetoothPrintDriver.STATE_LISTEN:

				case BluetoothPrintDriver.STATE_NONE:
					
					String message = "Tidak dapat terhubung ke Printer Bluetooth. Pastikan Printer Bluetooth anda aktif.";
					
					//mActivity.showMessage(message);
					mActivity.setMessage(message);

					break;
				}
				break;

			case MESSAGE_WRITE:
				break;

			case MESSAGE_READ:

				String ErrorMsg = null;
				byte[] readBuf = (byte[]) msg.obj;
				float Voltage = 0;

				if (D) {
					Log.i(TAG, "readBuf[0]:" + readBuf[0] + "  readBuf[1]:" + readBuf[1] + "  readBuf[2]:" + readBuf[2]);
				}

				if (readBuf[2] == 0) {
					ErrorMsg = "NO ERROR!";
				}

				else {

					if ((readBuf[2] & 0x02) != 0)
						ErrorMsg = "ERROR: No printer connected!";

					if ((readBuf[2] & 0x04) != 0)
						ErrorMsg = "ERROR: No paper!  ";

					if ((readBuf[2] & 0x08) != 0)
						ErrorMsg = "ERROR: Voltage is too low!  ";

					if ((readBuf[2] & 0x40) != 0)
						ErrorMsg = "ERROR: Printer Over Heat!  ";
				}

				Voltage = (float) ((readBuf[0] * 256 + readBuf[1]) / 10.0);

				mActivity.showMessage(ErrorMsg + "Battery voltage : " + Voltage + " V");
				break;

			case MESSAGE_DEVICE_NAME:

				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				mActivity.showMessage("Connected to " + mConnectedDeviceName);
				break;

			case MESSAGE_TOAST:

				mActivity.showMessage(msg.getData().getString(TOAST));
				break;
			}
		}
	};
	
	public static void print(Transactions transaction, List<TransactionItem> transactionItems) {

		/*if (BluetoothPrintDriver.IsNoConnection()) {

			Intent serverIntent = new Intent(this, CashierPaymentDeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
		}*/

		BluetoothPrintDriver.WakeUpPritner();
		
		BluetoothPrintDriver.Begin();

		// width
		// BluetoothPrintDriver.SetFontEnlarge((byte)0x10);

		// height
		// BluetoothPrintDriver.SetFontEnlarge((byte)0x01);

		// underline
		// BluetoothPrintDriver.SetUnderline((byte)0x02);

		// bold
		// BluetoothPrintDriver.SetBold((byte)0x01);

		// mini font
		BluetoothPrintDriver.SetCharacterFont((byte) 0x01);

		// highlight
		// BluetoothPrintDriver.SetBlackReversePrint((byte)0x01);

		String spaces = "                                        ";
		String divider = "----------------------------------------";
		
		StringBuffer line = new StringBuffer();
		
		Merchant merchant = MerchantUtil.getMerchant();
		
		line.append(merchant.getName());
		
		line.append(spaces.substring(0, mPrinterLineSize - merchant.getName().length()));
		BluetoothPrintDriver.BT_Write(line.toString() + "\r");
		
		BluetoothPrintDriver.BT_Write(divider.substring(0, mPrinterLineSize) + "\r");
		
		String addrAndTelp = merchant.getAddress();
		
		if (!CommonUtil.isEmpty(merchant.getTelephone())) {
			 addrAndTelp += " Tlp. " + merchant.getTelephone();
		}
		
		String[] addrAndTelps = addrAndTelp.split(" ");
		
		String addrAndTlpLine = Constant.EMPTY_STRING;
		
		for (String str : addrAndTelps) {
			
			if (addrAndTlpLine.length() + str.length() < mPrinterLineSize) {
				
				addrAndTlpLine = addrAndTlpLine + str + " "; 
			
			} else {
				
				line.setLength(0);
				line.append(addrAndTlpLine);
				line.append(spaces.substring(0, mPrinterLineSize - addrAndTlpLine.length()));
				
				BluetoothPrintDriver.BT_Write(line.toString() + "\r");
				
				addrAndTlpLine = str + " ";
			}
		}
		
		line.setLength(0);
		line.append(addrAndTlpLine);
		line.append(spaces.substring(0, mPrinterLineSize - addrAndTlpLine.length()));
		BluetoothPrintDriver.BT_Write(line.toString() + "\r");
		
		BluetoothPrintDriver.BT_Write(divider.substring(0, mPrinterLineSize) + "\r");
		
		String transDateText = CommonUtil.formatDateTime(transaction.getTransactionDate()); 
		
		line.setLength(0);
		line.append(transDateText);
		line.append(spaces.substring(0, mPrinterLineSize - transDateText.length()));
		BluetoothPrintDriver.BT_Write(line.toString() + "\r");
		
		String cashierText = "Kasir : " + transaction.getCashierName();
		
		line.setLength(0);
		line.append(cashierText);
		line.append(spaces.substring(0, mPrinterLineSize - cashierText.length()));
		BluetoothPrintDriver.BT_Write(line.toString() + "\r");
		
		BluetoothPrintDriver.BT_Write(divider.substring(0, mPrinterLineSize) + "\r");
		
		for (TransactionItem item : transactionItems) {
			
			String productName = item.getProductName();
			
			if (productName.length() > mPrinterLineSize) {
				productName = productName.substring(0, mPrinterLineSize);
			}
			
			int quantity = item.getQuantity();
			int price = item.getPrice();
			int total = quantity * price;
			
			line.setLength(0);
			
			line.append(productName);
			line.append(spaces.substring(0, mPrinterLineSize - productName.length()));
			
			BluetoothPrintDriver.BT_Write(line.toString() + "\r");
			
			String quantityStr = String.valueOf(quantity);
			String priceStr = CommonUtil.formatCurrency(price);
			String totalStr = CommonUtil.formatCurrency(total);
			
			line.setLength(0);
			
			line.append(quantityStr);
			line.append(spaces.substring(0, 3 - quantityStr.length()));
			line.append("x  ");
			line.append(priceStr);
			line.append(spaces.substring(0, mPrinterLineSize - line.toString().length() - totalStr.length()));
			line.append(totalStr);
			
			BluetoothPrintDriver.BT_Write(line.toString() + "\r");
			
		}
		
		BluetoothPrintDriver.BT_Write(divider.substring(0, mPrinterLineSize) + "\r");
		
		String billAmount = CommonUtil.formatCurrency(transaction.getBillAmount()); 
		
		line.setLength(0);
		line.append(spaces.substring(0, mPrinterLineSize - billAmount.length()));
		line.append(billAmount);
		
		BluetoothPrintDriver.BT_Write(line.toString() + "\r");
		
		BluetoothPrintDriver.BT_Write(spaces.substring(0, mPrinterLineSize) + "\r");
		
		if (transaction.getDiscountName() != null) {
			
			String discountLabel = transaction.getDiscountName() + " " + transaction.getDiscountPercentage() + "%";
			String discountText = "- " + CommonUtil.formatCurrency(transaction.getDiscountAmount());
			
			line.setLength(0);
			line.append(discountLabel);
			line.append(spaces.substring(0, mPrinterLineSize - discountLabel.length() - discountText.length()));
			line.append(discountText);
			
			BluetoothPrintDriver.BT_Write(line.toString() + "\r");
		}
		
		
		
		if (transaction.getTaxAmount() != 0) {
		
			String taxLabel = "Pajak " + transaction.getTaxPercentage() + "%";
			String taxText = CommonUtil.formatCurrency(transaction.getTaxAmount());
			
			line.setLength(0);
			line.append(taxLabel);
			line.append(spaces.substring(0, mPrinterLineSize - taxLabel.length() - taxText.length()));
			line.append(taxText);
			
			BluetoothPrintDriver.BT_Write(line.toString() + "\r");
		}
		
		if (transaction.getServiceChargeAmount() != 0) {
			
			String serviceChargeLabel = "Service Charge " + transaction.getServiceChargePercentage() + "%";
			String serviceChargeText = CommonUtil.formatCurrency(transaction.getServiceChargeAmount());
			
			line.setLength(0);
			line.append(serviceChargeLabel);
			line.append(spaces.substring(0, mPrinterLineSize - serviceChargeLabel.length() - serviceChargeText.length()));
			line.append(serviceChargeText);
			
			BluetoothPrintDriver.BT_Write(line.toString() + "\r");
		}
		
		String totalLabel = "Total";
		String returnLabel = "Kembali";
		
		String paymentLabel =  CodeUtil.getPaymentTypeLabel(transaction.getPaymentType());

		String totalText = CommonUtil.formatCurrency(transaction.getTotalAmount());
		String paymentText = CommonUtil.formatCurrency(transaction.getPaymentAmount());
		String returnText = CommonUtil.formatCurrency(transaction.getReturnAmount());
		
		line.setLength(0);
		
		BluetoothPrintDriver.BT_Write(divider.substring(0, mPrinterLineSize) + "\r");
		
		line.setLength(0);
		
		line.append(totalLabel);
		line.append(spaces.substring(0, mPrinterLineSize - totalLabel.length() - totalText.length()));
		line.append(totalText);

		BluetoothPrintDriver.BT_Write(line.toString() + "\r");

		line.setLength(0);
		line.append(paymentLabel);
		line.append(spaces.substring(0, mPrinterLineSize - paymentLabel.length() - paymentText.length()));
		line.append(paymentText);

		BluetoothPrintDriver.BT_Write(line.toString() + "\r");

		BluetoothPrintDriver.BT_Write(divider.substring(0, mPrinterLineSize) + "\r");

		line.setLength(0);
		line.append(returnLabel);
		line.append(spaces.substring(0, mPrinterLineSize - returnLabel.length() - returnText.length()));
		line.append(returnText);

		BluetoothPrintDriver.BT_Write(line.toString() + "\r");

		BluetoothPrintDriver.BT_Write(spaces.substring(0, mPrinterLineSize) + "\r");
		BluetoothPrintDriver.BT_Write(spaces.substring(0, mPrinterLineSize) + "\r");
		BluetoothPrintDriver.BT_Write(spaces.substring(0, mPrinterLineSize) + "\r");
	}
}

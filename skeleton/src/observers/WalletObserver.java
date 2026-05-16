package observers;

public interface WalletObserver {
    void onWalletChanged(int newBalance);
}

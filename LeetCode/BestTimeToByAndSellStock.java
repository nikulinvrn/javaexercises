package LeetCode;

public class BestTimeToByAndSellStock {

    public static void main(String[] args) {

    }

    public int maxProfit(int[] prices) {
        int profit = 0;
        int lastBestPriceToBuy = 10001;

        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < lastBestPriceToBuy) {
                lastBestPriceToBuy = prices[i];
            }
            if (profit < prices[i] - lastBestPriceToBuy) {
                profit = prices[i] - lastBestPriceToBuy;
            }

        }
        return profit;
    }
}

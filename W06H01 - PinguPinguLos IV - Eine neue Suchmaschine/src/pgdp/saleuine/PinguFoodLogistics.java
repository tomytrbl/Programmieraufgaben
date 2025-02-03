package pgdp.saleuine;

import java.math.BigDecimal;

import static pgdp.saleuine.PinguLib.randomInt;

public class PinguFoodLogistics {
	private TradeOrderQueue orderBook;
	private BigDecimal ppgAnchovies;
	private BigDecimal ppgCrustaceans;
	private BigDecimal ppgSardines;
	private int amountunusedA;
	private int weightA;
	private int weightS;
	private int weightC;
	private int amountunusedC;
	private int amountunusedS;
	private int weightunused;

	public PinguFoodLogistics(BigDecimal ppgAnchovies, BigDecimal ppgCrustaceans, BigDecimal ppgSardines) {
		this.ppgAnchovies = ppgAnchovies;
		this.ppgCrustaceans = ppgCrustaceans;
		this.ppgSardines = ppgSardines;
		orderBook = new TradeOrderQueue();
	}

	public void acceptNewOrder(TradeOrder order){
		orderBook.add(order);}


	private void registerUnusedFood(PinguFood food){
		if (food instanceof Anchovie){
			amountunusedA += 1;
			weightA += food.getWeight();
		} else if (food instanceof  Crustacean) {
			amountunusedC +=1;
			weightC += food.getWeight();
		} else if (food instanceof Sardine) {
			amountunusedS +=1;
			weightS += food.getWeight();
		}
		weightunused += food.getWeight();
	}
	public void printWasteStatistics(){
		int total = amountunusedA+amountunusedS+amountunusedC;
		BigDecimal A = new BigDecimal(weightA);
		BigDecimal S = new BigDecimal(weightS);
		BigDecimal C = new BigDecimal(weightC);
		BigDecimal pA = ppgAnchovies.multiply(A);
		BigDecimal pC = ppgCrustaceans.multiply(C);
		BigDecimal pS = ppgSardines.multiply(S);
		BigDecimal profit = pA.add(pC).add(pS);

		System.out.println("Bisher konnten "+total+" Tiere mit einem Gesamtgewicht von "+weightunused+"g nicht verwertet werden.\nClaudia und Karl-Heinz ist dadurch ein Profit von "+profit+"PD entgangen.");
	}
	public void clearOrderBook(){
		System.out.println("Es können "+orderBook.size()+" Bestellungen abgearbeitet werden.");
		int size = orderBook.size();
		for (int i = 0; i < size; i++) {
			TradeOrder current = orderBook.poll();
			//Kann AmountOrder oder WeightOrder sein
			if(current instanceof WeightOrder){
				while (((WeightOrder) current).getTargetWeight() > current.getCurrentWeight()){
					PinguFood weightOrder = generatePinguFood();
					if (!weightOrder.isEdible()) {
						registerUnusedFood(weightOrder);
					} else {
						if (weightOrder instanceof Anchovie) {
							current.supplyOrder(weightOrder, ppgAnchovies.multiply(new BigDecimal(weightOrder.getWeight())));
						} else if (weightOrder instanceof Sardine) {
							current.supplyOrder(weightOrder, ppgSardines.multiply(new BigDecimal(weightOrder.getWeight())));
						} else if (weightOrder instanceof Crustacean) {
							current.supplyOrder(weightOrder, ppgCrustaceans.multiply(new BigDecimal(weightOrder.getWeight())));
						}
					}
				}
			}
			else if(current instanceof AmountOrder){
				while (!current.isOrderFulfilled()){
					if (((AmountOrder) current).getCurrentAmountAnchovies() < ((AmountOrder) current).getTargetAmountAnchovies()){
						PinguFood anchovie = generateAnchovie();
						if (!anchovie.isEdible()) {
							registerUnusedFood(anchovie);
						} else {
							current.supplyOrder(anchovie, ppgAnchovies.multiply(new BigDecimal(anchovie.getWeight())));
						}
					}
					else if (((AmountOrder) current).getCurrentAmountCrustaceans() < ((AmountOrder) current).getTargetAmountCrustaceans()){
						PinguFood crustacean = generateCrustacean();
						if (!crustacean.isEdible()) {
							registerUnusedFood(crustacean);
						} else {
						current.supplyOrder(crustacean, ppgCrustaceans.multiply(new BigDecimal(crustacean.getWeight())));
					}}
					else if (((AmountOrder) current).getCurrentAmountSardines() < ((AmountOrder) current).getTargetAmountSardines()){
						PinguFood sardine = generateSardine();
						if (!sardine.isEdible()) {
							registerUnusedFood(sardine);
						} else {
						current.supplyOrder(sardine, ppgSardines.multiply(new BigDecimal(sardine.getWeight())));
					}}
				}
			}
			else{while(!current.isOrderFulfilled()){
				PinguFood food =generatePinguFood();
				if(!food.isEdible()){
					registerUnusedFood(food);
				}else{
					if (food instanceof Anchovie) {
						current.supplyOrder(food, ppgAnchovies.multiply(new BigDecimal(food.getWeight())));
					} else if (food instanceof Sardine) {
						current.supplyOrder(food, ppgSardines.multiply(new BigDecimal(food.getWeight())));
					} else if (food instanceof Crustacean) {
						current.supplyOrder(food, ppgCrustaceans.multiply(new BigDecimal(food.getWeight())));
					}
				}
			}
			}
			System.out.println(current.toString());
		}
//		System.out.println("Es können "+Anzahl Bestellungen in orderBook>⎵Bestellungen⎵abgearbeitet⎵werden.");
		//
	}


	public static void main(String[] args) {
		PinguFoodLogistics market = new PinguFoodLogistics(BigDecimal.ONE, BigDecimal.valueOf(0.5),
				BigDecimal.valueOf(2));
		market.acceptNewOrder(new TradeOrder());
		market.acceptNewOrder(new WeightOrder(1000));
		market.acceptNewOrder(new AmountOrder(2, 2, 2));
		market.clearOrderBook();
		market.printWasteStatistics();
	}

	/**
	 * The following methods generate Anchovie, Crustacean or Sardine object
	 * WARNING: do NOT change these methods unless you want to fail the tests
	 */


	public static PinguFood generatePinguFood() {
		switch (randomInt(0, 2)) {
		case 0:
			return generateAnchovie();
		case 1:
			return generateCrustacean();
		case 2:
			return generateSardine();
		default:
			throw new SecurityException("You changed the code!");
		}
	}

	public static Anchovie generateAnchovie() {
		return new Anchovie(randomInt(0, 5), randomInt(1, 55));
	}

	public static Crustacean generateCrustacean() {
		return new Crustacean(randomInt(1, 10));
	}

	public static Sardine generateSardine() {
		return new Sardine(randomInt(0, 10), randomInt(20, 300), randomInt(1, 22));
	}
}

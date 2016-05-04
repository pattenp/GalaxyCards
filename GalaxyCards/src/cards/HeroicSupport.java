package cards;

import java.io.Serializable;

import enumMessage.Lanes;
import enumMessage.Persons;
import guiPacket.Card;

/**
 * 
 * @author 13120dde
 *
 */
public class HeroicSupport extends Card implements PlayCardsInterface, Serializable, Target {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8195967465017595877L;
	private int defense;
	private final int PRICE;
	private final String NAME, RARITY, IMAGE_NAME;
	private boolean hasAbility;
	private boolean tapped = true;
	private int maxHp;
	private Lanes ENUM;

	/**
	 * Constructor instantiates this card with given arguments to configure its
	 * visuals by calling upon it's parent's methods. The rarity argument must
	 * be "common", "rare" or "legendary" (not case sensitive and no
	 * whitespace). The imageName argument must match the image's name and the
	 * imagefile must be in pictures directory.
	 * 
	 * 
	 * @param name
	 *            : String
	 * @param rarity
	 *            : String
	 * @param imageName
	 *            : String
	 * @param hasAbility
	 *            : boolean
	 * 
	 * @param defense
	 *            : int
	 * @param PRICE
	 *            : int
	 */
	public HeroicSupport(String name, String rarity, String imageName, boolean hasAbility, int price, int defense) {
		NAME = name;
		RARITY = rarity;
		IMAGE_NAME = imageName;
		this.hasAbility = hasAbility;
		this.PRICE = price;
		this.defense = defense;
		setType(this);
		setName(NAME);
		setRarity(RARITY);
		setImage(IMAGE_NAME);
		hasAbility(this.hasAbility);
		setPrice(this.PRICE);
		super.setDefense(this.defense);
		this.maxHp = defense;
	}

	/**
	 * Sets the booelan value tapped to true. Units and Heroic support cards
	 * that are tapped cant interact this round.
	 */
	public void tap() {
		tapped = true;
	}

	/**
	 * Sets the boolean value tapped to false. Units and Heroic support cards
	 * that are untapped can interact this round.
	 */
	public void untap() {
		tapped = false;
	}

	/**
	 * Returns the tapped state for this object.
	 * 
	 * @return : true/false
	 */
	public boolean getTap() {
		return tapped;
	}

	/**
	 * Returns the name of the image used by this card. Image's are located in
	 * pictures directory.
	 * 
	 * @return IMAGE_NAME : String
	 */
	public String getImage() {
		return IMAGE_NAME;
	}

	/**
	 * Returns the name of this card.
	 * 
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * Returns the rarity of this card.
	 * 
	 * @return RARITY : String
	 */
	public String getRarity() {
		return RARITY;
	}

	/**
	 * Return true if this card has ability, else false.
	 * 
	 * @return :boolean
	 */
	public boolean hasAbility() {
		return hasAbility;
	}

	public void setAbilityText(String description) {
		super.setAbilityText(description);
	}

	/**
	 * Retruns the cost to play this card.
	 * 
	 * @return PRICE : int
	 */
	public int getPrice() {
		return PRICE;
	}

	/**
	 * Retruns the amount of defence this card has left.
	 * 
	 * @return defense : int
	 */
	public int getDefense() {
		return defense;
	}

	/**
	 * Sets defense by inrementing with amount passed in as argument. If a
	 * negative value is passed in, defense decreases.
	 */
	public void setDefense(int amount) {
		this.defense = amount;
		super.setDefense(this.defense);
	}

	// /**
	// * Returns a String with the description of this card.
	// *
	// * @return : String
	// */
	// public String toString() {
	// return NAME + " - [HeroicSupport] Rarity: " + RARITY + ", image name: " +
	// IMAGE_NAME + ", Defense: " + defense
	// + ", Price: " + price + ", Has ability:" + hasAbility;
	// }

	/**
	 * Returns a String with the description of this card.
	 * 
	 * @return : String
	 */
	public String toString() {
		return NAME + " - [Heroic Support]: " + "0/" + defense + " Tapped: "+getTap();

	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	/**
	 * Sets the enum that represent who this cardobject belongs to. This method
	 * is called upon whenever this card is being played and placed on the
	 * gameboard.
	 * 
	 * @param eNUM
	 *            : Persons
	 */
	public void setLanesEnum(Lanes ENUM) {
		this.ENUM = ENUM;
	}

	/**
	 * Gets the enunm that represent who this cardobject belongs to.
	 * 
	 * @return ENUM : Persons
	 */
	public Lanes getLanesEnum() {
		return ENUM;
	}
	
	public void updateHp(int currentHp) {
		this.defense = currentHp;
		super.setDefense(currentHp);
	}
	
	@Override
	public void damage(int amt) {
		this.defense -= amt;
	}

	@Override
	public int getDamage() {
		return 0;
	}

	@Override
	public boolean isDead() {
		if (defense <= 0) {
			return true;
		}
		return false;
	}	
}
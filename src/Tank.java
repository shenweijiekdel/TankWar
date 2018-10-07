import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class Tank extends Drawable implements Impackable{
	private int speed = 3;
	private int missleSpeed;
	private int level;
	private int direction;
	private int health = 3;
	private int point = 0;
	private int count = 0;
	private boolean isImpacked;
	private boolean isDeading;
	private Image[] boomImage = {
			new ImageIcon("drawable/boom/1.png").getImage(),
			new ImageIcon("drawable/boom/2.png").getImage(),
			new ImageIcon("drawable/boom/3.png").getImage(),
			new ImageIcon("drawable/boom/4.png").getImage(),
			new ImageIcon("drawable/boom/5.png").getImage(),
			new ImageIcon("drawable/boom/3.png").getImage(),
			new ImageIcon("drawable/boom/1.png").getImage(),
	};
	private Image[] moveUp = {
			new ImageIcon("drawable/tank/1/m1-U-1.png").getImage(),
			new ImageIcon("drawable/tank/1/m1-U-2.png").getImage() };
	private Image[] moveDown = {
			new ImageIcon("drawable/tank/1/m1-D-1.png").getImage(),
			new ImageIcon("drawable/tank/1/m1-D-2.png").getImage() };
	private Image[] moveLeft = {
			new ImageIcon("drawable/tank/1/m1-L-1.png").getImage(),
			new ImageIcon("drawable/tank/1/m1-L-2.png").getImage() };
	private Image[] moveRight = {
			new ImageIcon("drawable/tank/1/m1-R-1.png").getImage(),
			new ImageIcon("drawable/tank/1/m1-R-2.png").getImage() };
	private Map<Integer, Image[]> p = new HashMap<Integer, Image[]>();
	{
		level = 1;
		p.put(Direction.UP, moveUp);
		p.put(Direction.DOWN, moveDown);
		p.put(Direction.LEFT, moveLeft);
		p.put(Direction.RIGHT, moveRight);
		// TODO Auto-generated constructor stub
	}
	@Override
	public  void impack(Impackable impackable){
		isImpacked();
		if (!isDeading){

		if (impackable instanceof Missle && ((Missle) impackable).isAlive()){
			((Missle)impackable).isImpacked();
			health -= ((Missle)impackable).getDamage();
		}
		if (health <= 0){
			isDeading = true;
			point = 0;
		}
		}


	}
	@Override
	public void isImpacked(){
		isImpacked = true;
		System.out.println("impacked");
	}
	public Tank(int x,int y,int width,int height,int direction,int speed,int missleSpeed){
		super(x,y,width,height);
		this.direction = direction;
		this.speed = speed;
		this.missleSpeed = missleSpeed;
		
	}
	public void setMissleSpeed(int missleSpeed){
		this.missleSpeed = missleSpeed;
	}
	public Missle fire(){
		if (!isDeading){

		if (this.direction == Direction.UP)
		return  new Missle(getX() + getWidth()/2-5,getY()-Missle.HEIGHT,missleSpeed,this.direction);
		if (this.direction == Direction.DOWN)
			return  new Missle(getX() + getWidth()/2-5,getY()+getHeight() ,missleSpeed,this.direction);
		if (this.direction == Direction.LEFT)
			return  new Missle(getX() - Missle.WIDTH ,getY()+getHeight()/2-5,missleSpeed,this.direction);
		if (this.direction == Direction.RIGHT)
			return  new Missle(getX() + getWidth(),getY()+getHeight()/2-5,missleSpeed,this.direction);
		else return null;
		}
		return null;
	}
	public void startUp(int direction){
		this.direction = direction;
		move();
		
	}
	public void move(){
	if (!isDeading && !isImpacked){

		switch(direction){

		case Direction.UP:
			setY(getY() - speed);
			break;
		case Direction.DOWN:
			setY(getY() + speed);
			break;
		case Direction.LEFT:
			setX(getX() - speed);
			break;
		case Direction.RIGHT:
			setX(getX() + speed);
			break;
		}
		if (point > 1)
			point = 0;
		point ++;
	}
	}


	public int getDirection(){
		return direction;
	}
	@Override
	public void draw(Graphics g) {
		if (!isDeading){
		g.drawImage(p.get(direction)[point % 2 ], getX(), getY(),(direction == Direction.UP || direction == Direction.DOWN)? getWidth():getHeight(), (direction == Direction.UP || direction == Direction.DOWN)?getHeight():getWidth(), null);

		} else {
			g.drawImage(boomImage[point],getX() + getWidth()/2 - boomImage[point].getWidth(null)/2,getY() + getHeight()/2 - boomImage[point].getHeight(null)/2,boomImage[point].getWidth(null),boomImage[point].getHeight(null),null);
			if (count == 2){
				count = 0;
				point ++;
			}
			else
				count ++;
			if (point  == 6){
				setDead(true);
				return ;
			}
		}
	}
}

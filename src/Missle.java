import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;


public class Missle extends Drawable implements Impackable{
	public static int WIDTH = 10;
	public static int HEIGHT = 12;
	private int penetration = 1;
	private int speed = 4;
	private int damage = 1;
	private int direction;
	private int count = 0;
	private int point = 0;
	private boolean isAlive = true;
	private Image[] boomImage = new Image[]{
			new ImageIcon("drawable/boom/1.png").getImage(),
			new ImageIcon("drawable/boom/3.png").getImage(),
			new ImageIcon("drawable/boom/2.png").getImage(),
	};
	private Image[] missleImage = new Image[4];
	private Map<Integer,Image> missle = new HashMap<Integer,Image>();
	private Image[] missleDead;
	{
		missleImage[0] = new ImageIcon("drawable/Missle/Missle_Up.png").getImage();
		missleImage[1] = new ImageIcon("drawable/Missle/Missle_Down.png").getImage();
		missleImage[2] = new ImageIcon("drawable/Missle/Missle_Left.png").getImage();
		missleImage[3] = new ImageIcon("drawable/Missle/Missle_Right.png").getImage();
		missle.put(Direction.UP, missleImage[0]);
		missle.put(Direction.DOWN, missleImage[1]);
		missle.put(Direction.LEFT, missleImage[2]);
		missle.put(Direction.RIGHT, missleImage[3]);
	}
	public Missle(int x,int y,int speed,int direction){
		super(x,y,WIDTH,HEIGHT);
		this.direction = direction;
		this.speed = speed;
		
	}
	public int getDamage(){
		return this.damage;
	}
	@Override
	public void impack(Impackable impackable){
		if (isAlive){

		if (impackable instanceof Tank)
		impackable.impack(this);
			 impackable.isImpacked();
			isImpacked();
		}
	}
	@Override
	public void isImpacked(){     //子弹和坦克的碰撞关系优化一下,在坦克中调用子弹的消亡动画不太好
		isAlive = false;
	}
	public void logic(){
		if (isAlive)
		switch (direction){
		case Direction.UP:setY(getY() - speed);break;
		case Direction.DOWN:setY(getY() + speed);break;
		case Direction.LEFT:setX(getX() - speed);break;
		case Direction.RIGHT:setX(getX() + speed);break;
		}

	}
	public boolean isAlive(){
		return isAlive;
	}
	@Override
	public void draw(Graphics g){
		if (isAlive)
		g.drawImage(missle.get(direction), getX(), getY(),(direction == Direction.UP || direction == Direction.DOWN)? getWidth():getHeight(),(direction == Direction.UP || direction == Direction.DOWN)? getHeight():getWidth(),null);
		else {
			g.drawImage(boomImage[point],getX() + getWidth()/2 - boomImage[point].getWidth(null)/2,getY() + getHeight()/2 - boomImage[point].getHeight(null)/2,boomImage[point].getWidth(null),boomImage[point].getHeight(null),null);
			if (count == 3){
				System.out.println(count);
				count = 0;
				point ++;
			}
			else
				count ++;
			if (point  == 2 ){
				setDead(true);
				return ;
			}
		}
	}
}

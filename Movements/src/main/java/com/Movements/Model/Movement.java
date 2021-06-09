package com.Movements.Model;
import java.awt.geom.Point2D;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

@Validated
@Entity
public class Movement {
	@Id
	private int IDHelper;
	@NotNull
	private double currentPosX;
	@NotNull
	private double currentPosY;
	@NotNull
	private double objectivePosX;
	@NotNull
	private double objectivePosY;
	
	public Movement() {
	}

	public Movement(int id,double currentPosX, double currentPosY, double objectivePosX, double objectivePosY) {
		super();
		this.IDHelper=id;
		this.currentPosX = currentPosX;
		this.currentPosY = currentPosY;
		this.objectivePosX = objectivePosX;
		this.objectivePosY = objectivePosY;
	}

	public double getCurrentPosX() {
		return currentPosX;
	}

	public void setCurrentPosX(double currentPosX) {
		this.currentPosX = currentPosX;
	}

	public double getCurrentPosY() {
		return currentPosY;
	}

	public void setCurrentPosY(double currentPosY) {
		this.currentPosY = currentPosY;
	}

	public double getObjectivePosY() {
		return objectivePosY;
	}

	public void setObjectivePosY(double objectivePosY) {
		this.objectivePosY = objectivePosY;
	}
	
	public double getObjectivePosX() {
		return objectivePosX;
	}

	public void setObjectivePosX(double objectivePosX) {
		this.objectivePosX = objectivePosX;
	}
	
	public void setObjectivePos(Point2D newPos) {
		this.setObjectivePosX(newPos.getX());
		this.setObjectivePosY(newPos.getY());
	}
	
	public int getIDHelper() {
		return IDHelper;
	}

	public void setIDHelper(int id) {
		this.IDHelper = id;
	}
	
	public Point2D getCurrentPos() {
		return new Point2D.Double(currentPosX, currentPosY);
	}
	
	public Point2D getObjectivePos() {
		return new Point2D.Double(objectivePosX, objectivePosY);
	}
	
	public void setCurentPos(Point2D newPos) {
		this.setCurrentPosX(newPos.getX());
		this.setCurrentPosY(newPos.getY());
	}

	@Override
	public String toString() {
		return "Movement ["+this.IDHelper+"]: posX:"+this.currentPosX+", posY:"+this.currentPosY+", objectivePosX:"+this.objectivePosX+" objectivePosY:"+this.objectivePosY;
	}
}

package com.carrental.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class RentalReturnRequest {
	@PositiveOrZero(message = "Extra charges cannot be negative")
    private double extraCharges;

    @NotBlank(message = "Return condition cannot be blank")
    private String returnCondition;

    @Size(max = 500, message = "Inspection notes cannot exceed 500 characters")
    private String inspectionNotes;

	public double getExtraCharges() {
		return extraCharges;
	}
	public void setExtraCharges(double extraCharges) {
		this.extraCharges = extraCharges;
	}
	public String getReturnCondition() {
		return returnCondition;
	}
	public void setReturnCondition(String returnCondition) {
		this.returnCondition = returnCondition;
	}
	public String getInspectionNotes() {
		return inspectionNotes;
	}
	public void setInspectionNotes(String inspectionNotes) {
		this.inspectionNotes = inspectionNotes;
	}
	
}

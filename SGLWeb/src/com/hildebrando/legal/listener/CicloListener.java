package com.hildebrando.legal.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class CicloListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		 System.out.println("DESPUES DE FASE: "+event.getPhaseId());
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("ANTES DE FASE: "+event.getPhaseId());
		
	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.ANY_PHASE;
	}

	
}

package com.callsign.ticketly.constants;

public enum TicketPriority {
    H1(500), H2(60), H3(30),
    M1(400), M2(50), M3(20),
    L1(300), L2(40), L3(10);

    private final int weight;

    TicketPriority(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }
}

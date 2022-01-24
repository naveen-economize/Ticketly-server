package com.callsign.ticketly.constants;

public enum CustomerType {
    VIP(TicketPriority.H1, TicketPriority.H2, TicketPriority.H3),
    loyal(TicketPriority.M1, TicketPriority.M2, TicketPriority.M3),
    recent(TicketPriority.L1, TicketPriority.L2, TicketPriority.L3);

    private final TicketPriority highPriority;
    private final TicketPriority mediumPriority;
    private final TicketPriority lowPriority;

    CustomerType(TicketPriority highPriority, TicketPriority mediumPriority, TicketPriority lowPriority) {
        this.highPriority = highPriority;
        this.mediumPriority = mediumPriority;
        this.lowPriority = lowPriority;
    }

    public TicketPriority getHighPriority(){
        return this.highPriority;
    }

    public TicketPriority getMediumPriority(){
        return this.mediumPriority;
    }

    public TicketPriority getLowPriority(){
        return this.lowPriority;
    }
}

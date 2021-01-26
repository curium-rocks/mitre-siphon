package xyz.andrewkboyd.mitresiphon.dto;

import lombok.Data;

/**
 * Echo result wrapper, this model wraps a received number
 * for echoing back to clients
 */
public @Data
class EchoResult {
    private final int echoNumber;
}

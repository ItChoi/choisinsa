package core.common.enumeration.event;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EventType {
    INVITE_FRIEND("친구 초대"),
    RAFFLE("래플"),
    RANDOM("랜덤"),
    LOOK_BOOK("룩북"),
    EXPERIENCE("체험단");

    private final String text;
}

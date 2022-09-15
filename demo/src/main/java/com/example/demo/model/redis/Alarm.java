package com.example.demo.model.redis;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.io.Serializable;

@Getter @Setter
@Builder
public class Alarm  implements Serializable {
	private static final long serialVersionUID = 6494678977089006639L;
	@Nullable
	private String id;

	@NotNull
	private String senderId;

	private String senderName;

	@NotNull
	private Alarm.AlarmType alarmType;

	public enum AlarmType {
		FRIEND, DUO, AGREE , MESSAGE
	}
}

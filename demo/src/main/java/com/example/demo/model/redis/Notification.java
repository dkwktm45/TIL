package com.example.demo.model.redis;

import com.example.demo.model.redis.Alarm;
import com.sun.istack.NotNull;
import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Builder
public class Notification implements Serializable {
	private static final long serialVersionUID = 6494678977089006639L;
	@NotNull
	private String receiveId;
	@ElementCollection
	@Builder.Default
	@CollectionTable(name = "userParticipant",joinColumns = @JoinColumn(name="receiveId"))
	private Set<Alarm> alarmList = new HashSet<>();

}

package edu.icet.service.custom;

import edu.icet.service.SuperService;

public interface UserLogService extends SuperService {
    String lastLoggedUser();
}
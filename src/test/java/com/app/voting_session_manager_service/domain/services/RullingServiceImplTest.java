package com.app.voting_session_manager_service.domain.services;

import com.app.voting_session_manager_service.domain.exceptions.RullingAlreadyExistsException;
import com.app.voting_session_manager_service.domain.exceptions.RullingTitleInvalidException;
import com.app.voting_session_manager_service.factories.RullingFactory;
import com.app.voting_session_manager_service.factories.RullingRegisterDTOFactory;
import com.app.voting_session_manager_service.resources.repositories.RullingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RullingServiceImplTest {

    @Autowired
    private RullingService rullingService;

    @MockitoBean
    private RullingRepository rullingRepository;

    @Test
    void testRegister_whenRegisterAValidTitle_shouldBeRegister() {
        var rullingRegisterDTOMock = RullingRegisterDTOFactory.sample();
        var rullingMock = RullingFactory.sample();

        doReturn(Optional.empty()).when(rullingRepository).findByTitle(any());
        doReturn(rullingMock).when(rullingRepository).save(any());

        rullingService.register(rullingRegisterDTOMock);

        verify(rullingRepository, times(1)).findByTitle(any());
        verify(rullingRepository, times(1)).save(any());
    }

    @Test
    void testRegister_whenRullingTitleAlreadyExists_shouldBeAnException() {
        var rullingRegisterDTOMock = RullingRegisterDTOFactory.sample();
        var rullingMock = RullingFactory.sample();

        doReturn(Optional.of(rullingMock)).when(rullingRepository).findByTitle(any());

        assertThrows(RullingAlreadyExistsException.class, () -> {
            rullingService.register(rullingRegisterDTOMock);
        });
    }

    @Test
    void testRegister_whenRullingTitleIsInvalid_shouldBeAnException() {
        var rullingRegisterDTOMock = RullingRegisterDTOFactory.sampleWithInvalidTitle();
        var rullingMock = RullingFactory.sample();

        doReturn(Optional.empty()).when(rullingRepository).findByTitle(any());

        assertThrows(RullingTitleInvalidException.class, () -> {
            rullingService.register(rullingRegisterDTOMock);
        });
    }
}

package com.ridehailing.rider.service.impl;

import com.ridehailing.rider.dto.RiderRegistrationRequest;
import com.ridehailing.rider.dto.RiderResponse;
import com.ridehailing.rider.entity.Rider;
import com.ridehailing.rider.exception.RiderNotFoundException;
import com.ridehailing.rider.repository.RiderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RiderServiceImplTest {

    @Mock
    private RiderRepository riderRepository;

    @InjectMocks
    private RiderServiceImpl riderService;

    @Test
    void registerRiderCreatesAndReturnsRider() {
        RiderRegistrationRequest request = registrationRequest();
        when(riderRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(riderRepository.existsByPhoneNumber(request.getPhoneNumber())).thenReturn(false);
        when(riderRepository.save(any(Rider.class))).thenAnswer(invocation -> {
            Rider rider = invocation.getArgument(0);
            rider.setId(42L);
            return rider;
        });

        RiderResponse response = riderService.registerRider(request);

        ArgumentCaptor<Rider> riderCaptor = ArgumentCaptor.forClass(Rider.class);
        verify(riderRepository).save(riderCaptor.capture());
        Rider savedRider = riderCaptor.getValue();

        assertThat(savedRider.getFirstName()).isEqualTo("Asha");
        assertThat(savedRider.getLastName()).isEqualTo("Patel");
        assertThat(savedRider.getEmail()).isEqualTo("asha.patel@example.com");
        assertThat(savedRider.getPhoneNumber()).isEqualTo("+919876543210");
        assertThat(savedRider.getCurrentLocation()).isEqualTo("Bengaluru");

        assertThat(response.getId()).isEqualTo(42L);
        assertThat(response.getFirstName()).isEqualTo("Asha");
        assertThat(response.getLastName()).isEqualTo("Patel");
        assertThat(response.getEmail()).isEqualTo("asha.patel@example.com");
        assertThat(response.getPhoneNumber()).isEqualTo("+919876543210");
        assertThat(response.getCurrentLocation()).isEqualTo("Bengaluru");
    }

    @Test
    void registerRiderRejectsDuplicateEmail() {
        RiderRegistrationRequest request = registrationRequest();
        when(riderRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> riderService.registerRider(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email already registered");

        verify(riderRepository, never()).existsByPhoneNumber(any());
        verify(riderRepository, never()).save(any());
    }

    @Test
    void getRiderByIdThrowsWhenRiderDoesNotExist() {
        when(riderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> riderService.getRiderById(99L))
                .isInstanceOf(RiderNotFoundException.class)
                .hasMessage("Rider not found with id: 99");
    }

    @Test
    void updateRiderLocationSavesNewLocation() {
        Rider rider = rider();
        when(riderRepository.findById(42L)).thenReturn(Optional.of(rider));
        when(riderRepository.save(rider)).thenReturn(rider);

        RiderResponse response = riderService.updateRiderLocation(42L, "Mumbai");

        assertThat(rider.getCurrentLocation()).isEqualTo("Mumbai");
        assertThat(response.getCurrentLocation()).isEqualTo("Mumbai");
        verify(riderRepository).save(rider);
    }

    @Test
    void deleteRiderDeletesExistingRider() {
        when(riderRepository.existsById(42L)).thenReturn(true);

        riderService.deleteRider(42L);

        verify(riderRepository).deleteById(42L);
    }

    private RiderRegistrationRequest registrationRequest() {
        RiderRegistrationRequest request = new RiderRegistrationRequest();
        request.setFirstName("Asha");
        request.setLastName("Patel");
        request.setEmail("asha.patel@example.com");
        request.setPhoneNumber("+919876543210");
        request.setCurrentLocation("Bengaluru");
        return request;
    }

    private Rider rider() {
        Rider rider = new Rider();
        rider.setId(42L);
        rider.setFirstName("Asha");
        rider.setLastName("Patel");
        rider.setEmail("asha.patel@example.com");
        rider.setPhoneNumber("+919876543210");
        rider.setCurrentLocation("Bengaluru");
        return rider;
    }
}

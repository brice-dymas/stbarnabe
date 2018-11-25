/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { StBarnabeTestModule } from '../../../test.module';
import { CarteDeleteDialogComponent } from 'app/entities/carte/carte-delete-dialog.component';
import { CarteService } from 'app/entities/carte/carte.service';

describe('Component Tests', () => {
    describe('Carte Management Delete Component', () => {
        let comp: CarteDeleteDialogComponent;
        let fixture: ComponentFixture<CarteDeleteDialogComponent>;
        let service: CarteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StBarnabeTestModule],
                declarations: [CarteDeleteDialogComponent]
            })
                .overrideTemplate(CarteDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CarteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CarteService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

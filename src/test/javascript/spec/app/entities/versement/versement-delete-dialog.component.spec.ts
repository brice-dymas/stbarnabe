/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { StBarnabeTestModule } from '../../../test.module';
import { VersementDeleteDialogComponent } from 'app/entities/versement/versement-delete-dialog.component';
import { VersementService } from 'app/entities/versement/versement.service';

describe('Component Tests', () => {
    describe('Versement Management Delete Component', () => {
        let comp: VersementDeleteDialogComponent;
        let fixture: ComponentFixture<VersementDeleteDialogComponent>;
        let service: VersementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StBarnabeTestModule],
                declarations: [VersementDeleteDialogComponent]
            })
                .overrideTemplate(VersementDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VersementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VersementService);
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

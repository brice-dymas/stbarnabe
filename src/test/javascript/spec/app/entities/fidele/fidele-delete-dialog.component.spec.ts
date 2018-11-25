/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { StBarnabeTestModule } from '../../../test.module';
import { FideleDeleteDialogComponent } from 'app/entities/fidele/fidele-delete-dialog.component';
import { FideleService } from 'app/entities/fidele/fidele.service';

describe('Component Tests', () => {
    describe('Fidele Management Delete Component', () => {
        let comp: FideleDeleteDialogComponent;
        let fixture: ComponentFixture<FideleDeleteDialogComponent>;
        let service: FideleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StBarnabeTestModule],
                declarations: [FideleDeleteDialogComponent]
            })
                .overrideTemplate(FideleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FideleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FideleService);
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

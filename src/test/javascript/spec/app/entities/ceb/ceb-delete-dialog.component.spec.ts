/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { StBarnabeTestModule } from '../../../test.module';
import { CEBDeleteDialogComponent } from 'app/entities/ceb/ceb-delete-dialog.component';
import { CEBService } from 'app/entities/ceb/ceb.service';

describe('Component Tests', () => {
    describe('CEB Management Delete Component', () => {
        let comp: CEBDeleteDialogComponent;
        let fixture: ComponentFixture<CEBDeleteDialogComponent>;
        let service: CEBService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StBarnabeTestModule],
                declarations: [CEBDeleteDialogComponent]
            })
                .overrideTemplate(CEBDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CEBDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CEBService);
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

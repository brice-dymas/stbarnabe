/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { StBarnabeTestModule } from '../../../test.module';
import { CEBUpdateComponent } from 'app/entities/ceb/ceb-update.component';
import { CEBService } from 'app/entities/ceb/ceb.service';
import { CEB } from 'app/shared/model/ceb.model';

describe('Component Tests', () => {
    describe('CEB Management Update Component', () => {
        let comp: CEBUpdateComponent;
        let fixture: ComponentFixture<CEBUpdateComponent>;
        let service: CEBService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StBarnabeTestModule],
                declarations: [CEBUpdateComponent]
            })
                .overrideTemplate(CEBUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CEBUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CEBService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CEB(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.cEB = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CEB();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.cEB = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});

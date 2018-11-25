/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { StBarnabeTestModule } from '../../../test.module';
import { FideleUpdateComponent } from 'app/entities/fidele/fidele-update.component';
import { FideleService } from 'app/entities/fidele/fidele.service';
import { Fidele } from 'app/shared/model/fidele.model';

describe('Component Tests', () => {
    describe('Fidele Management Update Component', () => {
        let comp: FideleUpdateComponent;
        let fixture: ComponentFixture<FideleUpdateComponent>;
        let service: FideleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StBarnabeTestModule],
                declarations: [FideleUpdateComponent]
            })
                .overrideTemplate(FideleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FideleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FideleService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Fidele(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.fidele = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Fidele();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.fidele = entity;
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

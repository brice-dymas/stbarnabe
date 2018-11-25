/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { StBarnabeTestModule } from '../../../test.module';
import { VersementUpdateComponent } from 'app/entities/versement/versement-update.component';
import { VersementService } from 'app/entities/versement/versement.service';
import { Versement } from 'app/shared/model/versement.model';

describe('Component Tests', () => {
    describe('Versement Management Update Component', () => {
        let comp: VersementUpdateComponent;
        let fixture: ComponentFixture<VersementUpdateComponent>;
        let service: VersementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StBarnabeTestModule],
                declarations: [VersementUpdateComponent]
            })
                .overrideTemplate(VersementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VersementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VersementService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Versement(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.versement = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Versement();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.versement = entity;
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

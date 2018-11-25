/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { StBarnabeTestModule } from '../../../test.module';
import { CarteUpdateComponent } from 'app/entities/carte/carte-update.component';
import { CarteService } from 'app/entities/carte/carte.service';
import { Carte } from 'app/shared/model/carte.model';

describe('Component Tests', () => {
    describe('Carte Management Update Component', () => {
        let comp: CarteUpdateComponent;
        let fixture: ComponentFixture<CarteUpdateComponent>;
        let service: CarteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StBarnabeTestModule],
                declarations: [CarteUpdateComponent]
            })
                .overrideTemplate(CarteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CarteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CarteService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Carte(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.carte = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Carte();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.carte = entity;
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
